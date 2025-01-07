package kh.edu.rupp.ite.rentwise.model.setuproom.response

import RoomLocation
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.Floor
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.LandlordConfigurationsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RentalRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RoomType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class RoomAssignmentViewModel : ViewModel() {

    private val _rooms = MutableStateFlow<List<RoomLocation>>(emptyList())
    val rooms: StateFlow<List<RoomLocation>> = _rooms

    private val _roomTypes = MutableStateFlow<List<String>>(emptyList())
    val roomTypes: StateFlow<List<String>> = _roomTypes

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _submissionSuccess = MutableLiveData<Boolean>()
    val submissionSuccess: LiveData<Boolean> = _submissionSuccess

    private val roomAssignments = mutableMapOf<String, RoomAssignmentData>()

    data class RoomAssignmentData(
        val type: String,
        val waterUsage: Double,
        val electricityUsage: Double,
        val tenantCode: String,
        val description: String
    )

    /**
     * Update local in-memory assignment data for a specific room
     */
    fun updateRoomAssignment(
        roomId: String,
        type: String,
        waterUsage: Double,
        electricityUsage: Double,
        tenantCode: String,
        description: String
    ) {
        roomAssignments[roomId] = RoomAssignmentData(
            type = type,
            waterUsage = waterUsage,
            electricityUsage = electricityUsage,
            tenantCode = tenantCode,
            description = description
        )
        Log.d("RoomAssignment", "Updated room $roomId: $type")
    }

    /**
     * Submit all local room assignments to the server
     */
    fun submitRoomAssignments() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Convert local assignment map to a list of RentalRequest
                val rentals: List<RentalRequest> = roomAssignments.map { (roomId, data) ->
                    RentalRequest(
                        landlord_id = 1, // TODO: Replace with actual landlord_id
                        renter_id = data.tenantCode.toIntOrNull() ?: 0,
                        floor = _rooms.value.find { it.id == roomId }?.floor ?: 0,
                        room_number = _rooms.value.find { it.id == roomId }?.room ?: 0,
                        water_usage = data.waterUsage,
                        electricity_usage = data.electricityUsage,
                        room_type = data.type,
                        utility_price_id = 1, // TODO: Replace with actual utility price ID if needed
                        description = data.description
                    )
                }

                val assignRequest = AssignRoomsRequest(rentals)
                val response = RetrofitClient.instance.saveRoomAssignments(assignRequest)

                if (response.isSuccessful) {
                    _submissionSuccess.value = true
                    Log.d("RoomAssignment", "Assignments saved successfully")
                } else {
                    _error.value = "Failed to save assignments"
                }
            } catch (e: Exception) {
                _error.value = e.message
                Log.e("RoomAssignment", "Error saving assignments", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Fetch the landlord's *already saved* configuration from the server.
     * This should be a GET call (or similar) that returns floors, rooms, and room types.
     */
//    fun fetchRoomConfiguration(landlordId: Int = 1) {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                // ---- The key fix: Do NOT use a hardcoded LandlordConfigurationsRequest ----
//                // Instead, call a GET method that retrieves the saved config for a landlord:
//                val response = RetrofitClient.instance.getLandlordConfigurations(landlordId)
//                handleConfigResponse(response)
//            } catch (e: Exception) {
//                _error.value = "Error: ${e.message}"
//                Log.e("RoomAssignment", "Error", e)
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }

    /**
     * Handle server response (floors, rooms, room types)
     */
    private fun handleConfigResponse(response: Response<LandlordConfigurationsResponse>) {
        if (response.isSuccessful) {
            response.body()?.let { config ->
                // Convert raw List<String> room_types + List<RoomLocation> rooms
                // from the response into StateFlow values
                _roomTypes.value = config.data.room_types
                _rooms.value = config.data.rooms
                Log.d("RoomAssignment", "Configuration loaded successfully")
            } ?: run {
                _error.value = "Empty response"
            }
        } else {
            _error.value = "Error ${response.code()}: ${response.message()}"
        }
    }
}
