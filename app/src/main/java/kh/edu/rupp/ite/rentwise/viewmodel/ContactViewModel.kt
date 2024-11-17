package kh.edu.rupp.ite.rentwise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {

    private val _dueContactState = MutableLiveData<ApiState<User>>()
    val dueContactState: LiveData<ApiState<User>> get() = _dueContactState

    fun loadContact() {
        viewModelScope.launch {
            try {
                delay(3000)
                val user = RetrofitClient.instance.getUser() // Now returns User directly
                _dueContactState.postValue(ApiState(State.success, user))
            } catch (e: Exception) {
                _dueContactState.postValue(ApiState(State.error, null))
            }
        }
    }
}
