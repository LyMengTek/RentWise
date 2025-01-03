package kh.edu.rupp.ite.rentwise.model.setuproom.request

data class AssignRoomRequest(
    val landlord_id: Int,
    val renter_id: Int,
    val floor: Int,
    val room_number: Int,
    val water_usage: Double,
    val electricity_usage: Double,
    val room_type: String,
    val utility_price_id: Int,
    val description: String
)

// For multiple rooms
data class AssignMultipleRoomsRequest(
    val rentals: List<AssignRoomRequest>
)