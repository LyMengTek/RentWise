package kh.edu.rupp.ite.rentwise.model

data class Invoice(
    val id: Int,
    val rental_id: Int,
    val room_code: Int,
    val landlord_id: Int,
    val renter_id: Int,
    val amount_due: String,
    val due_date: String,
    val paid: Boolean,
    val rental: Rental // Matches nested object in API response
)