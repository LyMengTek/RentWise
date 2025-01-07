package kh.edu.rupp.ite.rentwise.model

data class LoginResponse(
    val status: String,
    val message: String,
    val user: User
)