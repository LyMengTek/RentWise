package kh.edu.rupp.ite.rentwise.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignMultipleRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.UtilityPricesRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.RoomTypePricesResponse
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.SetupStatusResponse
import kotlinx.coroutines.launch

class DataForAssignViewModel : ViewModel() {
    private val _setupStatusResponse = MutableLiveData<SetupStatusResponse>()
    val setupStatusResponse: LiveData<SetupStatusResponse> get() = _setupStatusResponse

    private val _roomTypePricesResponse = MutableLiveData<RoomTypePricesResponse>()
    val roomTypePricesResponse: LiveData<RoomTypePricesResponse> get() = _roomTypePricesResponse

    private val _utilityPriceId = MutableLiveData<Int>()
    val utilityPriceId: LiveData<Int> get() = _utilityPriceId

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _submissionSuccess = MutableLiveData<Boolean>()
    val submissionSuccess: LiveData<Boolean> get() = _submissionSuccess

    fun fetchFloorRoomData() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.getFloorRoomInfo()
                Log.d("ViewModel", "Floor room response: $response")
                _setupStatusResponse.value = response

                // After getting floor data, save utility prices
                saveUtilityPrices(1) // Assuming landlord_id is 1
            } catch (e: Exception) {
                Log.e("ViewModel", "Error fetching floor room data", e)
                _errorMessage.value = "Error fetching floor room data: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun saveUtilityPrices(landlordId: Int) {
        viewModelScope.launch {
            try {
                // Create utility prices request with all required parameters
                val utilityPricesRequest = UtilityPricesRequest(
                    landlord_id = landlordId,
                    electricity_price = 0.0,  // Set appropriate default or get from UI
                    water_price = 0.0         // Set appropriate default or get from UI
                )
                val response = RetrofitClient.instance.saveUtilityPrices(utilityPricesRequest)

                if (response.isSuccessful) {
                    response.body()?.let { utilityResponse ->
                        _utilityPriceId.postValue(utilityResponse.id)  // Use postValue instead of value
                        Log.d("ViewModel", "Utility price ID saved: ${utilityResponse.id}")
                    }
                } else {
                    _errorMessage.postValue("Failed to save utility prices")  // Use postValue
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error saving utility prices", e)
                _errorMessage.postValue("Error saving utility prices: ${e.message}")  // Use postValue
            }
        }
    }

    fun submitRoomAssignments(request: AssignMultipleRoomsRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d("ViewModel", "Submitting request: ${request.rentals}")
                val response = RetrofitClient.instance.saveAssignRoom(request)
                Log.d("ViewModel", "Response code: ${response.code()}")

                if (response.isSuccessful) {
                    _submissionSuccess.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ViewModel", "Error response body: $errorBody")
                    _errorMessage.value = "Failed to assign rooms: $errorBody"
                }
            } catch (e: Exception) {
                Log.e("ViewModel", "Error assigning rooms", e)
                _errorMessage.value = "Error assigning rooms: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetErrorMessage() {
        _errorMessage.value = null
    }
}