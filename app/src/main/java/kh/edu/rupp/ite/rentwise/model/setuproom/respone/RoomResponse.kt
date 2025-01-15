package kh.edu.rupp.ite.rentwise.model.setuproom.respone

data class RoomResponse(
    val room_types: List<String>,
    val rooms: List<Room>,
    val utility_price_id: Int
)
data class Room(
    val floor: Int,
    val room: Int
)
