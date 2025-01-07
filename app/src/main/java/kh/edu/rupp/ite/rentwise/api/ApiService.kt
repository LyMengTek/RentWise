package kh.edu.rupp.ite.rentwise.api

import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.model.setuproom.request.*
import kh.edu.rupp.ite.rentwise.model.setuproom.response.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Data classes to represent the login request and response
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String, val userId: String)

interface ApiService {
    @POST("/api/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @GET("/api/invoice")
    suspend fun getDueRoom(): Response<List<Invoice>>

    @GET("/api/user/1")
    suspend fun getUser(): Response<User>

    @POST("api/landlord-floor-rooms")
    suspend fun saveFloorRooms(@Body floorRoomsRequest: FloorRoomsRequest): Response<ResponseBody>

    @GET("/api/room-details/1")
    suspend fun getFloorRoomInfo(): Response<SetupStatusResponse>

    @POST("/api/rental/setup")
    suspend fun saveRoomAssignments(@Body assignRoomRequest: AssignRoomsRequest): Response<ResponseBody>

    @POST("api/landlord-configurations")
    suspend fun saveLandlordConfigurations(@Body request: LandlordConfigurationsRequest): Response<LandlordConfigurationsResponse>
}