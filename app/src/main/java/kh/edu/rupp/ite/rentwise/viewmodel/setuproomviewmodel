package kh.edu.rupp.ite.rentwise.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.SetupStatusResponse
import kotlinx.coroutines.launch

class SetupRoomViewModel : ViewModel() {
    private val _dueSetupState = MutableLiveData<ApiState<SetupStatusResponse>>()
    val dueSetupState: LiveData<ApiState<SetupStatusResponse>> = _dueSetupState

    fun loadDueSetup() {
        viewModelScope.launch {
            _dueSetupState.value = ApiState(State.loading, null)
            try {
                val setupData = RetrofitClient.instance.getFloorRoomInfo()
                _dueSetupState.value = ApiState(State.success, setupData)
            } catch (e: Exception) {
                Log.e("SetupRoomViewModel", "Error fetching setup data: ${e.message}", e)
                _dueSetupState.value = ApiState(State.error, null)
            }
        }
    }
}