package kh.edu.rupp.ite.rentwise.activity.landloard_setup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import kh.edu.rupp.ite.rentwise.adapter.AssignRoomAdapter
import kh.edu.rupp.ite.rentwise.databinding.ActivityAssignRoomsToTypesBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.response.RoomAssignmentViewModel

class AssignRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssignRoomsToTypesBinding
    private lateinit var viewModel: RoomAssignmentViewModel
    private lateinit var adapter: AssignRoomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignRoomsToTypesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView() // Move before observeViewModel
        observeViewModel()
//        setupClickListeners()

//        viewModel.fetchRoomConfiguration()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[RoomAssignmentViewModel::class.java]
    }

    private fun setupRecyclerView() {
        adapter = AssignRoomAdapter(viewModel)
        binding.recyclerViewFloors.apply {
            layoutManager = LinearLayoutManager(this@AssignRoomActivity)
            adapter = this@AssignRoomActivity.adapter
        }
    }

//    private fun setupClickListeners() {
//        binding.backToHome.setOnClickListener { finish() }
//        binding.btnSave.setOnClickListener { saveRoomAssignments() }
//    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.roomTypes.collect { roomTypes ->
                adapter.updateRoomTypes(roomTypes)
            }
        }
        lifecycleScope.launch {
            viewModel.rooms.collect { rooms ->
                adapter.updateRooms(rooms)
                binding.recyclerViewFloors.visibility =
                    if (rooms.isNotEmpty()) View.VISIBLE else View.GONE
            }
        }
    }



//    private fun saveRoomAssignments() {
//        try {
//            val selectedRooms = adapter.getSelectedRooms(
//                landlordId = 1,  // Replace with actual landlord ID
//                utilityPriceId = 1  // Replace with actual utility price ID
//            )
//
//            if (selectedRooms.rentals.isEmpty()) {
//                Toast.makeText(
//                    this,
//                    "Please assign at least one room",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return
//            }
//
//            viewModel.submitRoomAssignments(selectedRooms)
//        } catch (e: Exception) {
//            Log.e("AssignRoom", "Error preparing room assignments", e)
//            Toast.makeText(
//                this,
//                "Error: ${e.message ?: "Unknown error occurred"}",
//                Toast.LENGTH_LONG
//            ).show()
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up any resources if needed
    }
}