package kh.edu.rupp.ite.rentwise.api

import kh.edu.rupp.ite.rentwise.model.ApiResponse
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.InvoiceRequest
import kh.edu.rupp.ite.rentwise.model.LoginRequest
import kh.edu.rupp.ite.rentwise.model.LoginResponse
import kh.edu.rupp.ite.rentwise.model.RegisterRequest
import kh.edu.rupp.ite.rentwise.model.Rental
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.model.setuproom.FloorRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.RoomTypePricesRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.UtilityPricesRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Interface defining the API endpoints
interface ApiService {
    @POST("/api/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/api/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): ApiResponse<RegisterRequest>

    // GET request to fetch rooms
    @GET("/api/invoice/byLandlord/{landlordId}")
    suspend fun getInvoicesByLandlord(@Path("landlordId") landlordId: Int): ApiResponse<List<Invoice>>

    @GET("/api/rentals/{id}")
    suspend fun getRental(@Path("id") id: Int): ApiResponse<List<Rental>>

    @GET("/api/user/{userId}")
    suspend fun getUser(@Path("userId") userId: String): ApiResponse<User>

    @POST("/api/create/invoice")
    suspend fun createInvoice(@Body invoiceRequest: InvoiceRequest): ApiResponse<List<Invoice>>
    // test here

    @POST("api/landlord-floor-rooms")
    fun saveFloorRooms(@Body floorRoomsRequest: FloorRoomsRequest): Call<ResponseBody>

    @POST("api/utility-prices")
    fun saveUtilityPrices(@Body utilityPricesRequest: UtilityPricesRequest): Call<ResponseBody>

    @POST("api/room-type-prices")
    fun saveRoomTypePrices(@Body roomTypePricesRequest: RoomTypePricesRequest): Call<ResponseBody>


}
