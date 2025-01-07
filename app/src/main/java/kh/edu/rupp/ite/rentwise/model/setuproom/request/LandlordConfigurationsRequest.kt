package kh.edu.rupp.ite.rentwise.model.setuproom.request

import kh.edu.rupp.ite.rentwise.model.Floor

data class LandlordConfigurationsRequest(
    val landlord_id: Int,
    val water_price: Double,
    val electricity_price: Double,
    val floors: List<Floor>,
    val room_types: List<RoomType>
)

data class RoomType(
    var type: String,
    var price: Double
)