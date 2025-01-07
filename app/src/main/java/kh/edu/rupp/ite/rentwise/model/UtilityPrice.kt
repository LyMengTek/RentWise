package kh.edu.rupp.ite.rentwise.model

data class UtilityPrice(
    val id: Int,
    val landlord_id: Int,
    val water_price: String,
    val electricity_price: String,
)
