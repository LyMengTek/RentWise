package kh.edu.rupp.ite.rentwise.model

data class InvoiceRequest(
    val rental_id: Int,
    val landlord_id: Int,
    val renter_id: Int,
    val new_water_usage: Int,
    val new_electricity_usage: Int,
    val other: Int
)