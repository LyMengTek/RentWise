package kh.edu.rupp.ite.rentwise.model.setuproom.respone

data class FloorRoomResponse(
    val message: String,
    val data: List<FloorRoomData>
)

data class FloorRoomData(
    val id: Int,
    val landlord_id: Int,
    val floor: Int,
    val room: Int,
    val created_at: String,
    val updated_at: String
)