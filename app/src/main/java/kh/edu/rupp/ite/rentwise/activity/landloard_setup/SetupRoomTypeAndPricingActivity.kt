package kh.edu.rupp.ite.rentwise.activity.landloard_setup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.databinding.ActivitySetupRoomTypeAndPricingBinding
import kh.edu.rupp.ite.rentwise.viewmodel.RoomSetupViewModel
import kh.edu.rupp.ite.rentwise.adapter.SetupRoom.FloorAdapter
import kh.edu.rupp.ite.rentwise.adapter.SetupRoom.RoomTypeAdapter
import kh.edu.rupp.ite.rentwise.model.Floor
import kh.edu.rupp.ite.rentwise.model.setuproom.request.LandlordConfigurationsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RoomType

class SetupRoomTypeAndPricingActivity : ComponentActivity() {
    private lateinit var binding: ActivitySetupRoomTypeAndPricingBinding
    private lateinit var viewModel: RoomSetupViewModel
    private var floorCount: Int = 0
    private var roomTypeCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupRoomTypeAndPricingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        setupViewModel()
        setupObservers()
        setupListeners()
    }

    private fun setupViews() {
        binding.recyclerViewRoomTypes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFloors.layoutManager = LinearLayoutManager(this)

        // Initialize with empty adapters
        binding.recyclerViewRoomTypes.adapter = RoomTypeAdapter(emptyList()) { selectedRoomType: RoomType ->
            Log.d("RoomTypeAdapter", "Selected Room Type: ${selectedRoomType.type}")
        }
        binding.recyclerViewFloors.adapter = FloorAdapter(emptyList())
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[RoomSetupViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.configResponse.observe(this) { response ->
            if (response != null) {
                Log.d("Setup", "Configuration saved successfully")
                Toast.makeText(this, "Configuration saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        viewModel.error.observe(this) { errorMessage ->
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.submissionSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun setupListeners() {
        setupTextWatchers()
        setupClickListeners()
    }

    private fun setupTextWatchers() {
        binding.editTextRoomCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val newFloorCount = s.toString().toIntOrNull() ?: 0
                if (newFloorCount != floorCount) {
                    floorCount = newFloorCount
                    updateFloorsList()
                }
            }
        })

        binding.editTextRoomTypeCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val newRoomTypeCount = s.toString().toIntOrNull() ?: 0
                if (newRoomTypeCount != roomTypeCount) {
                    roomTypeCount = newRoomTypeCount
                    updateRoomTypesList()
                }
            }
        })
    }

    private fun updateFloorsList() {
        val floors = (1..floorCount).map { floorNumber ->
            Floor(floor_number = floorNumber, room_count = 0)
        }
        (binding.recyclerViewFloors.adapter as FloorAdapter).updateFloors(floors)
    }

    private fun updateRoomTypesList() {
        val roomTypes = (1..roomTypeCount).map { index ->
            RoomType(type = "Type $index", price = 0.0)
        }
        binding.recyclerViewRoomTypes.adapter = RoomTypeAdapter(roomTypes) { selectedType ->
            Log.d("RoomTypeAdapter", "Selected Room Type: ${selectedType.type}")
        }
    }

    private fun setupClickListeners() {
        binding.backToHome.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener { saveConfigurations() }
    }

    private fun getUserIdFromSharedPreferences(): Int {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userIdString = sharedPreferences.getString("userId", null) // Retrieve as String
        return userIdString?.toIntOrNull() ?: -1 // Convert to Int, return -1 if conversion fails
    }

    private fun saveConfigurations() {
        try {
            Log.d("SaveConfig", "Starting save configuration process")

            // Get landlord ID from SharedPreferences and convert to Int
            val landlordId = getUserIdFromSharedPreferences()
            if (landlordId == -1) {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
                return
            }

            val electricityPrice = binding.editTextElectricityPrice.text.toString().toDoubleOrNull()
            val waterPrice = binding.editTextWaterPrice.text.toString().toDoubleOrNull()

            if (electricityPrice == null || electricityPrice <= 0) {
                Toast.makeText(this, "Please enter a valid electricity price", Toast.LENGTH_SHORT).show()
                return
            }

            if (waterPrice == null || waterPrice <= 0) {
                Toast.makeText(this, "Please enter a valid water price", Toast.LENGTH_SHORT).show()
                return
            }

            val floorAdapter = binding.recyclerViewFloors.adapter as? FloorAdapter
            val floors = floorAdapter?.getFloorsWithRoomCounts()?.map { (floorNumber, roomCount) ->
                Floor(floor_number = floorNumber, room_count = roomCount)
            } ?: emptyList()

            if (floors.isEmpty()) {
                Toast.makeText(this, "Please add at least one floor", Toast.LENGTH_SHORT).show()
                return
            }

            val roomTypeAdapter = binding.recyclerViewRoomTypes.adapter as? RoomTypeAdapter
            val roomTypes = roomTypeAdapter?.roomTypes ?: emptyList()

            if (roomTypes.isEmpty()) {
                Toast.makeText(this, "Please add at least one room type", Toast.LENGTH_SHORT).show()
                return
            }

            // Validate room types data
            if (roomTypes.any { it.type.isBlank() || it.price <= 0 }) {
                Toast.makeText(this, "Please fill in all room type details", Toast.LENGTH_SHORT).show()
                return
            }

            val request = LandlordConfigurationsRequest(
                landlord_id = landlordId, // Use the converted integer
                water_price = waterPrice,
                electricity_price = electricityPrice,
                floors = floors,
                room_types = roomTypes
            )

            Log.d("SaveConfig", "Request Details:")
            Log.d("SaveConfig", "Landlord ID: ${request.landlord_id}")
            Log.d("SaveConfig", "Water Price: ${request.water_price}")
            Log.d("SaveConfig", "Electricity Price: ${request.electricity_price}")
            Log.d("SaveConfig", "Floors: ${request.floors.map { "Floor ${it.floor_number}: ${it.room_count} rooms" }}")
            Log.d("SaveConfig", "Room Types: ${request.room_types.map { "Type: ${it.type}, Price: ${it.price}" }}")

            viewModel.submitLandlordConfigurations(request)

        } catch (e: Exception) {
            Log.e("SaveConfig", "Error saving configurations", e)
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}