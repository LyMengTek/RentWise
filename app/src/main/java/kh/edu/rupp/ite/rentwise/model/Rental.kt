package kh.edu.rupp.ite.rentwise.model

data class Rental(
    val id: Int,
    val landlord_id: Int,
    val renter_id: Int,
    val room_id: Int,
    val start_date: String,
    val end_date: String,
    var is_active: Boolean,
    val room: Room, // Matches "room" in API
    val landlord: User, // Matches "landlord" in API
    val renter: User, // Matches "renter" in API
    val utility_usage: UtilityUsage
)
