package kh.edu.rupp.ite.rentwise.model

//data class Room(
//    val floor: Int,
//    val roomNumber: String,
//)

//data class Room(
//    val floorLabel: String,
//    val roomLabel: String,
//    val electricity: String,
//    val water: String,
//    val description: String
//

data class Room(
    val floor: Int,
    val roomCount: Int,
    val id: Int,
    val utility_price_id: Int,
    val room_type_price_id: Int,
    val user_id: Int,
    val room_number: String,
    val description: String,
    val available: Boolean,
    val room_code: Int
)

data class RoomType(
    val name: String,
    val price: Float
)

