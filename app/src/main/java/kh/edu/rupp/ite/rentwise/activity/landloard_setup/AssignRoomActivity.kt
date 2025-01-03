package kh.edu.rupp.ite.rentwise.activity.landloard_setup

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.adapter.AssignRoomAdapter
import kh.edu.rupp.ite.rentwise.databinding.ActivityAssignRoomsToTypesBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignMultipleRoomsRequest
import kh.edu.rupp.ite.rentwise.viewmodel.DataForAssignViewModel

class AssignRoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssignRoomsToTypesBinding
    private lateinit var viewModel: DataForAssignViewModel
    private lateinit var adapter: AssignRoomAdapter
    private var utilityPriceId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignRoomsToTypesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView() // Add this line
        setupViewModel()
        setupSubmitButton() // Add this line
        observeViewModel()

        binding.backToHome.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewFloors.layoutManager = LinearLayoutManager(this)
        adapter = AssignRoomAdapter()
        binding.recyclerViewFloors.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[DataForAssignViewModel::class.java]
        viewModel.fetchFloorRoomData() // Remove the duplicate call
    }

        private fun observeViewModel() {
            viewModel.setupStatusResponse.observe(this) { response ->
                response?.let {
                    val floors = it.floors
                    val roomTypeNames = it.roomTypes.map { roomType -> roomType.name }
                    adapter.updateData(floors, roomTypeNames)
                }
            }

            viewModel.utilityPriceId.observe(this) { id ->
                utilityPriceId = id
                Log.d("AssignRoomActivity", "Received utility price ID: $id")
            }

            viewModel.roomTypePricesResponse.observe(this) { response ->
                response?.let {
                    val roomTypeNames = it.room_type_prices.map { price -> price.type }
                    adapter.updateRoomTypes(roomTypeNames)
                }
            }

            viewModel.isLoading.observe(this) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
                binding.btnSave.isEnabled = !isLoading
            }

            viewModel.errorMessage.observe(this) { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                    viewModel.resetErrorMessage()
                }
            }

            viewModel.submissionSuccess.observe(this) { success ->
                if (success) {
                    Toast.makeText(this, "Rooms assigned successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }

        private fun setupSubmitButton() {
            binding.btnSave.setOnClickListener {
                if (utilityPriceId == null) {
                    Toast.makeText(this, "Please wait for utility price setup", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val selectedRooms = adapter.getSelectedRooms(
                    landlordId = 1,
                    renterId = 2,
                    utilityPriceId = utilityPriceId!!
                )

                if (selectedRooms.isEmpty()) {
                    Toast.makeText(this, "Please select room types", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val request = AssignMultipleRoomsRequest(selectedRooms)
                Log.d("AssignRoomActivity", "Submitting request: $request")
                viewModel.submitRoomAssignments(request)
            }
        }
    }