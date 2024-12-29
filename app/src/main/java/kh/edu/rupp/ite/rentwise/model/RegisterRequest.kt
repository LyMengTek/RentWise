package kh.edu.rupp.ite.rentwise.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val phone_number: String,
    val user_type: String
)
