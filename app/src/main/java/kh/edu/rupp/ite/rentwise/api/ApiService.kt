package kh.edu.rupp.ite.rentwise.api

import kh.edu.rupp.ite.rentwise.Room
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Data classes to represent the login request and response
data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val token: String, val userId: String)

// Interface defining the API endpoints
interface ApiService {
    @POST("/api/login")  // Endpoint for login
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    // GET request to fetch rooms
    @GET("/api/rooms")
    fun getRooms(): Call<List<Room>>

}