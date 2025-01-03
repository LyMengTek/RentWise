package kh.edu.rupp.ite.rentwise.model.setuproom.respone

data class RoomTypePricesResponse(
    val success: Boolean,
    val room_type_prices: List<RoomTypePrice>,
    val message: String
)

data class RoomTypePrice(
    val id: Int,
    val landlord_id: Int,
    val type: String,
    val type_price: Double,
    val created_at: String,
    val updated_at: String
)