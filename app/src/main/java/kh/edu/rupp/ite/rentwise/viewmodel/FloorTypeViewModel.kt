//package kh.edu.rupp.ite.rentwise.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import kh.edu.rupp.ite.rentwise.api.RetrofitClient
//import kh.edu.rupp.ite.rentwise.model.ApiState
//import kh.edu.rupp.ite.rentwise.model.State
//import kh.edu.rupp.ite.rentwise.model.setuproom.respone.FloorTypeResponse
//import kotlinx.coroutines.launch
//
//class FloorTypeViewModel : ViewModel() {
//    private val TAG = "FloorTypeViewModel"
//    private val _floorTypeState = MutableLiveData<ApiState<FloorTypeResponse>>()
//    val floorTypeState: LiveData<ApiState<FloorTypeResponse>> get() = _floorTypeState
//
//    fun fetchFloorTypes() {
//        _floorTypeState.value = ApiState(State.loading, null)
//        Log.d(TAG, "Loading floor types...")
//
//        viewModelScope.launch {
//            try {
//                val response = RetrofitClient.instance.getFloorType()
//                Log.d(TAG, "API Response received - Status: ${response.status}")
//
//                if (response.status == "success") {
//                    response.data?.let { data ->
//                        Log.d(TAG, "Floor types data received successfully")
//                        Log.d(TAG, "Room types: ${data.room_types}")
//                        Log.d(TAG, "Rooms: ${data.rooms.map { "Floor: ${it.floor}, Room: ${it.room}" }}")
//                        _floorTypeState.postValue(ApiState(State.success, data))
//                    } ?: run {
//                        Log.e(TAG, "Response success but null data")
//                        _floorTypeState.postValue(ApiState(State.error, null))
//                    }
//                } else {
//                    Log.e(TAG, "API Error: ${response.message}")
//                    _floorTypeState.postValue(ApiState(State.error, null))
//                }
//            } catch (exception: Exception) {
//                Log.e(TAG, "Exception while fetching floor types", exception)
//                _floorTypeState.postValue(ApiState(State.error, null))
//            }
//        }
//    }
//}