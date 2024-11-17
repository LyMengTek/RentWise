package kh.edu.rupp.ite.rentwise.model

data class ApiState<T>(
    val state: State,
    val data: T?
)

enum class State {
    loading, success, error
}
