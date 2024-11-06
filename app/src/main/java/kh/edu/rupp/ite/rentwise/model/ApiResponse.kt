package kh.edu.rupp.ite.rentwise.model

data class ApiResponse<T>(
    val status: String,
    val massage: String,
    val data: T?
)
