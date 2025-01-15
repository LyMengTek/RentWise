package kh.edu.rupp.ite.rentwise.model

data class RoomData(
    val floor: Int,
    val room: Int,
    val electricity: Float,
    val water: Float,
    val description: String,
    val roomType: String,
    val tenantCode: String,
    val renterId: Int = tenantCode.toIntOrNull() ?: -1
)