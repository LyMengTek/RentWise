package kh.edu.rupp.ite.rentwise.model

data class UtilityUsage(
    val id : String,
    val rental_id: Int,
    val room_code: Int,
    val water_usage: String,
    val electricity_usage: String,
    val other: String,
)
