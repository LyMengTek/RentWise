package kh.edu.rupp.ite.rentwise.api

//import kh.edu.rupp.ite.rentwise.Room
import LandlordConfigurationsResponse
import kh.edu.rupp.ite.rentwise.model.ApiResponse
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.InvoiceRequest
import kh.edu.rupp.ite.rentwise.model.LoginRequest
import kh.edu.rupp.ite.rentwise.model.LoginResponse
import kh.edu.rupp.ite.rentwise.model.RegisterRequest
import kh.edu.rupp.ite.rentwise.model.Rental
import kh.edu.rupp.ite.rentwise.model.RenterDetailsWrapper
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.FloorRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.LandlordConfigurationsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RoomTypePricesRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.UtilityPricesRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
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
    @GET("/api/contact/byLandlord/23")
    suspend fun getContact(): ApiResponse<User>

    @POST("/api/rental/setup")
    suspend fun saveRoomAssignments(@Body assignRoomRequest: AssignRoomsRequest): Response<ResponseBody>

    @POST("api/landlord-configurations")
    suspend fun saveLandlordConfigurations(@Body request: LandlordConfigurationsRequest): Response<LandlordConfigurationsResponse>
}
