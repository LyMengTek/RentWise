package kh.edu.rupp.ite.rentwise.model.setuproom


data class RoomTypePricesRequest(
    val landlord_id: Int,
    val room_types: List<RoomType>
) {
    data class RoomType(
        val type: String,
        val price: Double
    )
}
