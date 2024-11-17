package kh.edu.rupp.ite.rentwise.activity.landloard_setup

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.adapter.FloorAdapter
import kh.edu.rupp.ite.rentwise.adapter.RoomTypeAdapter
import kh.edu.rupp.ite.rentwise.databinding.ActivitySetupRoomTypeAndPricingBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.FloorRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.RoomTypePricesRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.UtilityPricesRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetupRoomTypeAndPricingActivity : ComponentActivity() {

    private lateinit var binding: ActivitySetupRoomTypeAndPricingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupRoomTypeAndPricingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up RecyclerViews with LayoutManagers
        binding.recyclerViewRoomTypes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFloors.layoutManager = LinearLayoutManager(this)

        // Initialize RecyclerViews with empty adapters
        binding.recyclerViewRoomTypes.adapter = RoomTypeAdapter(emptyList())
        binding.recyclerViewFloors.adapter = FloorAdapter(emptyList())

        // Listeners for dynamically updating RecyclerView data
        binding.editTextRoomTypeCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here; this can remain empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed here; this can remain empty
            }

            override fun afterTextChanged(s: Editable?) {
                val typeCount = s.toString().toIntOrNull() ?: 0
                val roomTypes = List(typeCount) { kh.edu.rupp.ite.rentwise.viewmodel.RoomType("Type $it", 50.0f + it * 10) } // Make sure this RoomType belongs to model package
                binding.recyclerViewRoomTypes.adapter = RoomTypeAdapter(roomTypes)
            }
        })

        binding.editTextRoomCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed here; this can remain empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // No action needed here; this can remain empty
            }

            override fun afterTextChanged(s: Editable?) {
                val floorCount = s.toString().toIntOrNull() ?: 0
                binding.recyclerViewFloors.adapter = FloorAdapter(List(floorCount) { 0 })
            }
        })

        binding.backToHome.setOnClickListener {
            val intent = Intent(this, ShowRoomSetupOptionsActivity::class.java)
            startActivity(intent)
        }

        // Button to save room and floor setup
        binding.btnSave.setOnClickListener {
            val floorCount = binding.editTextRoomCount.text.toString().toIntOrNull() ?: 0
            val roomTypeCount = binding.editTextRoomTypeCount.text.toString().toIntOrNull() ?: 0

            // Prepare request data
            val floors = List(floorCount) { FloorRoomsRequest.Floor(it + 1, roomTypeCount) }
            val floorRoomsRequest = FloorRoomsRequest(1, floors)

            // Send data to API using Retrofit
            sendFloorRoomsData(floorRoomsRequest)

            val roomTypes = List(roomTypeCount) { RoomTypePricesRequest.RoomType("Type $it", 50.0 + it * 10) } // Use RoomType from model package
            val roomTypePricesRequest = RoomTypePricesRequest(1, roomTypes)
            sendRoomTypePricesData(roomTypePricesRequest)

            val utilityPricesRequest = UtilityPricesRequest(1.50, 2.75) // Example utility prices
            sendUtilityPricesData(utilityPricesRequest)
        }
    }

    private fun sendFloorRoomsData(floorRoomsRequest: FloorRoomsRequest) {
        val call = RetrofitClient.instance.saveFloorRooms(floorRoomsRequest)
        call.enqueue(object : Callback<ResponseBody> { // Changed to ResponseBody
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Data Saved Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    // Log the error message for debugging
                    Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Error Saving Data: ${response.code()}", Toast.LENGTH_SHORT).show()
                    // Log the full error response body if available
                    response.errorBody()?.let { errorBody ->
                        try {
                            val errorResponse = errorBody.string()
                            println("Error Response: $errorResponse") // This prints to the console/log
                        } catch (e: Exception) {
                            println("Error parsing error body: ${e.message}")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Network Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun sendRoomTypePricesData(roomTypePricesRequest: RoomTypePricesRequest) {
        val call = RetrofitClient.instance.saveRoomTypePrices(roomTypePricesRequest)
        call.enqueue(object : Callback<ResponseBody> { // Changed to ResponseBody
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Room Type Prices Saved Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Error Saving Room Type Prices", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Network Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun sendUtilityPricesData(utilityPricesRequest: UtilityPricesRequest) {
        val call = RetrofitClient.instance.saveUtilityPrices(utilityPricesRequest)
        call.enqueue(object : Callback<ResponseBody> { // Changed to ResponseBody
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Utility Prices Saved Successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Error Saving Utility Prices", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@SetupRoomTypeAndPricingActivity, "Network Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
