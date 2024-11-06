package kh.edu.rupp.ite.rentwise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiResponse
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class UpcomingViewModel : ViewModel() {
    
    private val _upcomingState = MutableLiveData<ApiState<List<Invoice>>>()
    val upcomingState: LiveData<ApiState<List<Invoice>>> get() = _upcomingState

    fun lordUpcomingRoom() {
        viewModelScope.launch {
            try {
                delay(3000)
                val upcomingResponse: ApiResponse<List<Invoice>> =
                    RetrofitClient.instance.getDueRoom()
                if (upcomingResponse.status == "success") {
                    _upcomingState.postValue(ApiState(State.success, upcomingResponse.data))
                } else {
                    _upcomingState.postValue(ApiState(State.error, null))
                }
            } catch (ex: Exception) {
                _upcomingState.postValue(ApiState(State.error, null))
            }
        }
    }
}