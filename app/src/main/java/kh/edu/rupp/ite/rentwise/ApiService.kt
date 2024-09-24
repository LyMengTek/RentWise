package kh.edu.rupp.ite.rentwise

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Data classes to represent the login request and response
data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val token: String, val userId: String)

// Interface defining the API endpoints
interface ApiService {
    @POST("/api/login")  // Endpoint for login
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}
