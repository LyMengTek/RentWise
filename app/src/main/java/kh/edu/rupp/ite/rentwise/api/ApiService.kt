package kh.edu.rupp.ite.rentwise.api

//import kh.edu.rupp.ite.rentwise.Room
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignMultipleRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.FloorRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RoomTypePricesRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.UtilityIdRespone
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.SetupStatusResponse
import kh.edu.rupp.ite.rentwise.model.setuproom.request.UtilityPricesRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.RoomTypePricesResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response


// Data classes to represent the login request and response
data class LoginRequest(val email: String, val password: String)

data class LoginResponse(val token: String, val userId: String)

// Interface defining the API endpoints
interface ApiService {
    @POST("/api/login")  // Endpoint for login
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    // GET request to fetch rooms
    @GET("/api/invoice")
    suspend fun getDueRoom(): List<Invoice>

    @GET("/api/user/1")
    suspend fun getUser(): User

    // test here

    @POST("api/landlord-floor-rooms")
    suspend fun saveFloorRooms(@Body floorRoomsRequest: FloorRoomsRequest): Response<ResponseBody>

   @POST("api/utility-prices")
   suspend fun saveUtilityPrices(@Body utilityPricesRequest: UtilityPricesRequest): Response<UtilityIdRespone>

   @POST("api/room-type-prices")
   suspend fun saveRoomTypePrices(@Body roomTypePricesRequest: RoomTypePricesRequest): Response<RoomTypePricesResponse>

    @GET("/api/room-details/1")
    suspend fun getFloorRoomInfo(): SetupStatusResponse

    @POST("/api/rental/setup")
    suspend fun saveAssignRoom(@Body assignRoomRequest: AssignMultipleRoomsRequest): Response<ResponseBody>

}
