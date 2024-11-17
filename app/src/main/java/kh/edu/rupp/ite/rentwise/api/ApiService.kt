package kh.edu.rupp.ite.rentwise.api

//import kh.edu.rupp.ite.rentwise.Room
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.model.setuproom.FloorRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.RoomTypePricesRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.UtilityPricesRequest
import okhttp3.ResponseBody
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
    @GET("/api/invoice")
    suspend fun getDueRoom(): List<Invoice>

    @GET("/api/user/1")
    suspend fun getUser(): User

    // test here

 @POST("api/landlord-floor-rooms")
 fun saveFloorRooms(@Body floorRoomsRequest: FloorRoomsRequest): Call<ResponseBody>

 @POST("api/utility-prices")
 fun saveUtilityPrices(@Body utilityPricesRequest: UtilityPricesRequest): Call<ResponseBody>

 @POST("api/room-type-prices")
 fun saveRoomTypePrices(@Body roomTypePricesRequest: RoomTypePricesRequest): Call<ResponseBody>


}
