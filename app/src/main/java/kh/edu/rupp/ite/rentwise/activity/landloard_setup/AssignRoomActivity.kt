package kh.edu.rupp.ite.rentwise.activity.landloard_setup

import RoomViewModel
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.adapter.AssignRoomAdapter
import kh.edu.rupp.ite.rentwise.databinding.ActivityAssignRoomsToTypesBinding
import kh.edu.rupp.ite.rentwise.model.RoomData
import kh.edu.rupp.ite.rentwise.model.State


class AssignRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssignRoomsToTypesBinding
    private lateinit var viewModel: RoomViewModel
    private lateinit var adapter: AssignRoomAdapter
    private val roomDataMap = mutableMapOf<Pair<Int, Int>, RoomData>()
    private var utilityPriceId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignRoomsToTypesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        adapter = AssignRoomAdapter(emptyList()) { roomData ->
            roomDataMap[Pair(roomData.floor, roomData.room)] = roomData
        }

        binding.recyclerViewFloors.apply {
            layoutManager = LinearLayoutManager(this@AssignRoomActivity)
            adapter = this@AssignRoomActivity.adapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[RoomViewModel::class.java]

        // Get landlordId from SharedPreferences
        val landlordId = getUserIdFromSharedPreferences()
        if (landlordId == -1) {
            Toast.makeText(this, "Error: Landlord ID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        viewModel.roomState.observe(this) { state ->
            when (state.state) {
                State.loading -> {
                    binding.progressBar.isVisible = true
                }
                State.success -> {
                    binding.progressBar.isVisible = false
                    state.data?.let { response ->
                        utilityPriceId = response.utility_price_id
                        Log.d("AssignRoomActivity", "Utility Price ID: $utilityPriceId")

                        adapter = AssignRoomAdapter(response.room_types) { roomData ->
                            roomDataMap[Pair(roomData.floor, roomData.room)] = roomData
                        }
                        binding.recyclerViewFloors.adapter = adapter
                        adapter.updateRooms(response.rooms)
                    }
                }
                State.error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this, "Error loading rooms", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.saveState.observe(this) { state ->
            when (state.state) {
                State.loading -> {
                    binding.progressBar.isVisible = true
                    binding.btnSave.isEnabled = false
                }
                State.success -> {
                    binding.progressBar.isVisible = false
                    binding.btnSave.isEnabled = true
                    Toast.makeText(this, "Rooms saved successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                State.error -> {
                    binding.progressBar.isVisible = false
                    binding.btnSave.isEnabled = true
                    Toast.makeText(this, "Error saving rooms", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Pass the landlordId to fetchRooms
        viewModel.fetchRooms(landlordId)
    }

    private fun getUserIdFromSharedPreferences(): Int {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userIdString = sharedPreferences.getString("userId", null)
        return userIdString?.toIntOrNull() ?: -1
    }

    private fun setupClickListeners() {
        binding.backToHome.setOnClickListener {
            finish()
        }

        binding.btnSave.setOnClickListener {
            if (validateRoomData()) {
                val landlordId = getUserIdFromSharedPreferences()
                if (landlordId != -1) {
                    // Check each room's renter ID before saving
                    val invalidRooms = roomDataMap.values.filter { it.renterId == -1 }
                    if (invalidRooms.isNotEmpty()) {
                        val firstInvalid = invalidRooms.first()
                        Toast.makeText(
                            this,
                            "Invalid tenant code for room ${firstInvalid.room} on floor ${firstInvalid.floor}. Please enter a valid numeric ID.",
                            Toast.LENGTH_LONG
                        ).show()
                        return@setOnClickListener
                    }

                    viewModel.saveRoomAssignments(
                        roomDataList = roomDataMap.values.toList(),
                        landlordId = landlordId,
                        utilityPriceId = utilityPriceId
                    )
                } else {
                    Toast.makeText(this, "Error: Landlord ID not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateRoomData(): Boolean {
        if (roomDataMap.isEmpty()) {
            Toast.makeText(this, "Please fill in room details", Toast.LENGTH_SHORT).show()
            return false
        }

        roomDataMap.values.forEach { roomData ->
            when {
                roomData.electricity <= 0 -> {
                    Toast.makeText(this, "Please enter valid electricity usage for room ${roomData.room} on floor ${roomData.floor}", Toast.LENGTH_SHORT).show()
                    return false
                }
                roomData.water <= 0 -> {
                    Toast.makeText(this, "Please enter valid water usage for room ${roomData.room} on floor ${roomData.floor}", Toast.LENGTH_SHORT).show()
                    return false
                }
                roomData.roomType.isEmpty() -> {
                    Toast.makeText(this, "Please select room type for room ${roomData.room} on floor ${roomData.floor}", Toast.LENGTH_SHORT).show()
                    return false
                }
                roomData.tenantCode.isEmpty() -> {
                    Toast.makeText(this, "Please enter tenant code for room ${roomData.room} on floor ${roomData.floor}", Toast.LENGTH_SHORT).show()
                    return false
                }
                roomData.renterId == -1 -> {
                    Toast.makeText(this, "Please enter a valid numeric tenant code for room ${roomData.room} on floor ${roomData.floor}", Toast.LENGTH_SHORT).show()
                    return false
                }
            }
        }
        return true
    }
}