package kh.edu.rupp.ite.rentwise.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.User
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {

    private val _dueContactState = MutableLiveData<ApiState<List<User>>>()
    val dueContactState: LiveData<ApiState<List<User>>> get() = _dueContactState

    fun fetchContacts() {
        _dueContactState.value = ApiState(State.loading) // Set state to loading

        viewModelScope.launch {
            try {
                // Call the API (landlordID is hardcoded to 23 in the endpoint)
                val response = RetrofitClient.instance.getContact()
                Log.d("ContactViewModel", "API Response: $response") // Log the API response

                if (response.status == "success") {
                    val user = response.data // Single User object
                    if (user != null) {
                        _dueContactState.postValue(ApiState(State.success, listOf(user))) // Set state to success with data
                    } else {
                        Log.e("ContactViewModel", "No data found in the API response") // Log error
                        _dueContactState.postValue(ApiState(State.error, errorMessage = "No data found")) // Set state to error
                    }
                } else {
                    Log.e("ContactViewModel", "API call failed: ${response.message}") // Log error
                    _dueContactState.postValue(ApiState(State.error, errorMessage = "API call failed")) // Set state to error
                }
            } catch (exception: Exception) {
                Log.e("ContactViewModel", "Exception occurred: ${exception.message}", exception) // Log exception
                _dueContactState.postValue(ApiState(State.error, errorMessage = exception.message)) // Set state to error
            }
        }
    }
}