package kh.edu.rupp.ite.rentwise.model

data class RenterResponse(
    val status: String,
    val message: String,
    val data: ArrayList<RenterData>  // Change to ArrayList
)
data class RenterData(
    val renter_details: RenterDetails
)

data class RenterDetails(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val profile_pic: String
)