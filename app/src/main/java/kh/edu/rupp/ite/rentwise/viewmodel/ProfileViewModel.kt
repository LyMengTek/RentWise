package kh.edu.rupp.ite.rentwise.viewmodel

import android.content.SharedPreferences
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

class ProfileViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _profileState = MutableLiveData<ApiState<User>>()
    val profileState: LiveData<ApiState<User>> get() = _profileState

    fun loadProfile(userId: String) {
        // Retrieve the token from SharedPreferences
        val token = sharedPreferences.getString("token", null)

        if (token.isNullOrEmpty()) {
            _profileState.postValue(ApiState(State.error, null))
            Log.e("ProfileViewModel", "Token is null or empty")
            return
        }

        // If token is present, make the API request to fetch the user profile
        viewModelScope.launch {
            try {
                // Make the authenticated API call using the token
                val response = RetrofitClient.instance.getUser(userId)

                // Log the entire response to check what you are getting
                Log.d("ProfileViewModel", "API Response: ${response.toString()}")

                // Check if the response is successful and contains data
                if (response.status == "success") {
                    val user = response.data
                    Log.d("ProfileViewModel", "User Data: ${user?.username}, ${user?.email}")
                    _profileState.postValue(ApiState(State.success, user))
                } else {
                    Log.e("ProfileViewModel", "Error: ${response.message}")
                    _profileState.postValue(ApiState(State.error, null))
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", "Error: ${e.localizedMessage}", e)
                _profileState.postValue(ApiState(State.error, null))
            }
        }
    }
}