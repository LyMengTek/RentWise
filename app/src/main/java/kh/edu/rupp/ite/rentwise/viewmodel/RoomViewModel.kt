import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.RoomData
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RentalRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.RoomResponse
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {
    private val _roomState = MutableLiveData<ApiState<RoomResponse>>()
    val roomState: LiveData<ApiState<RoomResponse>> get() = _roomState

    private val _saveState = MutableLiveData<ApiState<Unit>>()
    val saveState: LiveData<ApiState<Unit>> get() = _saveState

    fun fetchRooms(landlordId: Int) {
        _roomState.value = ApiState(State.loading, null)

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getFloorType(landlordId)
                Log.d("RoomViewModel", "Available Room Types: ${response.room_types.joinToString(", ")}")
                _roomState.postValue(ApiState(State.success, response))
            } catch (exception: Exception) {
                Log.e("RoomViewModel", "Error fetching rooms", exception)
                _roomState.postValue(ApiState(State.error, null))
            }
        }
    }

    fun saveRoomAssignments(roomDataList: List<RoomData>, landlordId: Int, utilityPriceId: Int) {
        _saveState.value = ApiState(State.loading, null)

        viewModelScope.launch {
            try {
                val rentalRequests = roomDataList.map { roomData ->
                    RentalRequest(
                        landlord_id = landlordId,
                        renter_id = roomData.renterId, // Use the renter ID from the room data
                        floor = roomData.floor,
                        room_number = roomData.room,
                        water_usage = roomData.water.toDouble(),
                        electricity_usage = roomData.electricity.toDouble(),
                        room_type = roomData.roomType.toLowerCase(),
                        utility_price_id = utilityPriceId,
                        description = roomData.description
                    )
                }

                val assignRoomsRequest = AssignRoomsRequest(rentals = rentalRequests)
                Log.d("RoomViewModel", "Request Payload: ${Gson().toJson(assignRoomsRequest)}")

                val response = RetrofitClient.instance.saveRoomAssignments(assignRoomsRequest)
                if (response.isSuccessful) {
                    _saveState.postValue(ApiState(State.success, Unit))
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("RoomViewModel", "Error Response: $errorBody")
                    _saveState.postValue(ApiState(State.error, null))
                }
            } catch (exception: Exception) {
                Log.e("RoomViewModel", "Error saving room assignments", exception)
                _saveState.postValue(ApiState(State.error, null))
            }
        }
    }
}