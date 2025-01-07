package kh.edu.rupp.ite.rentwise.model

data class User(
    val id: Int,
    val username: String,
    val profile_picture: String,
    val id_card_picture: String,
    val email: String,
    val user_type: String,
    val phone_number: String,
    val token: String,
)

