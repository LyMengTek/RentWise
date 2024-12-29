package kh.edu.rupp.ite.rentwise.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.RegisterRequest
import kh.edu.rupp.ite.rentwise.model.State
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableLiveData<ApiState<Any>>()
    val registerState: LiveData<ApiState<Any>> get() = _registerState

    fun registerUser(
        username: String,
        email: String,
        password: String,
        phoneNumber: String,
        userType: String
    ) {
        viewModelScope.launch {
            // Emit loading state
            _registerState.postValue(ApiState(State.loading, null))

            try {
                // Prepare the request
                val registerRequest = RegisterRequest(username, email, password, phoneNumber, userType)

                // Make the API call
                val response = RetrofitClient.instance.registerUser(registerRequest)

                // Check response status
                if (response.status == "success") {
                    _registerState.postValue(ApiState(State.success, response.data))
                } else {
                    _registerState.postValue(ApiState(State.error, null))
                    Log.e("RegisterViewModel", "Error response: ${response.message}")  // Using 'message' now
                }
            } catch (e: HttpException) {
                // Log the error in case of HttpException
                _registerState.postValue(ApiState(State.error, null))
                Log.e("RegisterViewModel", "HttpException: ${e.message()}")
            } catch (e: Exception) {
                // Catch other exceptions and log the details
                _registerState.postValue(ApiState(State.error, null))
                Log.e("RegisterViewModel", "Unexpected Error: ${e.localizedMessage}", e)
            }
        }
    }
}
