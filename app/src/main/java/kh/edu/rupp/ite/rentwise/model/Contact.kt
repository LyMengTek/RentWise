package kh.edu.rupp.ite.rentwise.model

data class Contact(
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val profile_pic: String?
)