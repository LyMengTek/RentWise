package kh.edu.rupp.ite.rentwise.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.Contact
import kotlinx.coroutines.launch

class ContactViewModel(private val context: Context) : ViewModel() {
    private val _dueContactState = MutableLiveData<ApiState<List<Contact>>>()
    val dueContactState: LiveData<ApiState<List<Contact>>> get() = _dueContactState

    private fun getUserIdFromSharedPreferences(): Int {
        val sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return try {
            val userIdString = sharedPreferences.getString("userId", null)
            userIdString?.toInt() ?: -1
        } catch (e: NumberFormatException) {
            Log.e("ContactViewModel", "Error parsing userId: ${e.message}")
            -1
        }
    }

    fun fetchContacts() {
        _dueContactState.value = ApiState(State.loading)

        viewModelScope.launch {
            try {
                val userId = getUserIdFromSharedPreferences()
                if (userId == -1) {
                    Log.e("ContactViewModel", "Invalid or missing user ID")
                    _dueContactState.postValue(ApiState(
                        state = State.error,
                        errorMessage = "User ID not found"
                    ))
                    return@launch
                }

                Log.d("ContactViewModel", "Fetching contacts for userId: $userId")

                val response = RetrofitClient.instance.getContact(userId)
                Log.d("ContactViewModel", "API Response: $response")

                when {
                    response.status == "success" && response.data != null -> {
                        val contact = response.data
                        _dueContactState.postValue(ApiState(
                            state = State.success,
                            data = listOf(contact)
                        ))
                    }
                    response.status == "success" && response.data == null -> {
                        Log.e("ContactViewModel", "Response successful but no data")
                        _dueContactState.postValue(ApiState(
                            state = State.error,
                            errorMessage = "No contact data found"
                        ))
                    }
                    else -> {
                        Log.e("ContactViewModel", "API error: ${response.message}")
                        _dueContactState.postValue(ApiState(
                            state = State.error,
                            errorMessage = response.message ?: "Unknown error occurred"
                        ))
                    }
                }
            } catch (e: Exception) {
                Log.e("ContactViewModel", "Exception in fetchContacts", e)
                _dueContactState.postValue(ApiState(
                    state = State.error,
                    errorMessage = "Error: ${e.localizedMessage}"
                ))
            }
        }
    }
}