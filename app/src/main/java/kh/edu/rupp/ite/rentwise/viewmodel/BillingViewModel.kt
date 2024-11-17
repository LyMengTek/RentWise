package kh.edu.rupp.ite.rentwise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BillingViewModel : ViewModel() {

    private val _dueRoomState = MutableLiveData<ApiState<List<Invoice>>>()
    val dueRoomState: LiveData<ApiState<List<Invoice>>> get() = _dueRoomState

    fun loadDueRoom() {
        viewModelScope.launch {
            try {
                delay(3000)
                val dueRoom = RetrofitClient.instance.getDueRoom()
                _dueRoomState.postValue(ApiState(State.success, dueRoom))
            }catch (e: Exception) {
                _dueRoomState.postValue(ApiState(State.error, null))
            }

        }

    }

}