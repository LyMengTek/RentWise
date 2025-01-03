package kh.edu.rupp.ite.rentwise.model.setuproom.request

data class UtilityPricesRequest(
    val landlord_id: Int,
    val electricity_price: Double,
    val water_price: Double
)

