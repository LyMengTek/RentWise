package kh.edu.rupp.ite.rentwise.model

data class ApiState<T>(
    val state: State,
    val data: T? = null,
    val errorMessage: String? = null // Add error message
)

enum class State {
    loading,
    success,
    error
}
