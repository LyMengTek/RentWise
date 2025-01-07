package kh.edu.rupp.ite.rentwise.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.setuproom.request.LandlordConfigurationsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.response.LandlordConfigurationsResponse
import kotlinx.coroutines.launch
import retrofit2.Response

class RoomSetupViewModel : ViewModel() {

    private val _configResponse = MutableLiveData<LandlordConfigurationsResponse?>()
    val configResponse: LiveData<LandlordConfigurationsResponse?> = _configResponse

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _submissionSuccess = MutableLiveData<Boolean>()
    val submissionSuccess: LiveData<Boolean> = _submissionSuccess

    fun submitLandlordConfigurations(request: LandlordConfigurationsRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = RetrofitClient.instance.saveLandlordConfigurations(request)
                if (response.isSuccessful) {
                    // Store the entire configuration response:
                    _configResponse.value = response.body()
                    _submissionSuccess.value = true
                } else {
                    _error.value = "Error ${response.code()}: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
