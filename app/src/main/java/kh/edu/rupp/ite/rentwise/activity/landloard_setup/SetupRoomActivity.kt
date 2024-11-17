//package kh.edu.rupp.ite.rentwise.activity.landloard_setup
//
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.yourpackage.models.FloorRoomsRequest
//import com.yourpackage.models.UtilityPricesRequest
//import com.yourpackage.models.RoomTypePricesRequest
//import com.yourpackage.network.RetrofitClient
//import kh.edu.rupp.ite.rentwise.api.RetrofitClient
//import kh.edu.rupp.ite.rentwise.model.setuproom.FloorRoomsRequest
//import kh.edu.rupp.ite.rentwise.model.setuproom.RoomTypePricesRequest
//import kh.edu.rupp.ite.rentwise.model.setuproom.UtilityPricesRequest
//import okhttp3.ResponseBody
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class SetupRoomActivity : AppCompatActivity() {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_setup_room)
//
//        // Example: Collect data for floors, prices, and room types
//        val floors = listOf(
//            FloorRoomsRequest.Floor(1, 3),
//            FloorRoomsRequest.Floor(2, 2),
//            FloorRoomsRequest.Floor(3, 5)
//        )
//        val floorRoomsRequest = FloorRoomsRequest(1, floors)
//        sendFloorRoomsData(floorRoomsRequest)
//
//        val utilityPricesRequest = UtilityPricesRequest(1.50, 2.75)
//        sendUtilityPricesData(utilityPricesRequest)
//
//        val roomTypes = listOf(
//            RoomTypePricesRequest.RoomType("Single", 50.0),
//            RoomTypePricesRequest.RoomType("Double", 75.0),
//            RoomTypePricesRequest.RoomType("Suite", 120.0)
//        )
//        val roomTypePricesRequest = RoomTypePricesRequest(1, roomTypes)
//        sendRoomTypePricesData(roomTypePricesRequest)
//    }
//
//    private fun sendFloorRoomsData(floorRoomsRequest: FloorRoomsRequest) {
//        val call = RetrofitClient.apiService.saveFloorRooms(floorRoomsRequest)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@SetupRoomActivity, "Floor Rooms Saved Successfully", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@SetupRoomActivity, "Error Saving Floor Rooms", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(this@SetupRoomActivity, "Network Failure", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun sendUtilityPricesData(utilityPricesRequest: UtilityPricesRequest) {
//        val call = RetrofitClient.apiService.saveUtilityPrices(utilityPricesRequest)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@SetupRoomActivity, "Utility Prices Saved Successfully", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@SetupRoomActivity, "Error Saving Utility Prices", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(this@SetupRoomActivity, "Network Failure", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    private fun sendRoomTypePricesData(roomTypePricesRequest: RoomTypePricesRequest) {
//        val call = RetrofitClient.apiService.saveRoomTypePrices(roomTypePricesRequest)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(this@SetupRoomActivity, "Room Type Prices Saved Successfully", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this@SetupRoomActivity, "Error Saving Room Type Prices", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(this@SetupRoomActivity, "Network Failure", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}