package kh.edu.rupp.ite.rentwise.activity.landloard_setup

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import kh.edu.rupp.ite.rentwise.databinding.ActivityAssignRoomsToTypesBinding
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding
import kh.edu.rupp.ite.rentwise.viewmodel.RoomType
import kh.edu.rupp.ite.rentwise.viewmodel.RoomTypeSetupViewModel

class AssignRoomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssignRoomsToTypesBinding
    private val viewModel: RoomTypeSetupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignRoomsToTypesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe roomTypes LiveData
        viewModel.roomTypes.observe(this, Observer { roomTypes ->
            // Clear previous views if needed
            binding.roomContainer.removeAllViews()

            // Inflate view holder for each room type and add to roomContainer
            roomTypes.forEach { roomType ->
                val roomBinding = ViewHolderRoomBinding.inflate(LayoutInflater.from(this), binding.roomContainer, false)

                // Set values from the room type
                roomBinding.floorLabel.text = "Floor ${roomType.name}" // Example, adjust as needed
                roomBinding.RoomLabel.text = roomType.name
                roomBinding.editTextFloor.setText(roomType.price.toString()) // Assuming electricity price
                roomBinding.editTextRoomCount.setText(roomType.price.toString()) // Assuming water price
//                roomBinding.editTextDescription.setText("Description for ${roomType.name}") // Placeholder description

                // Add the inflated view to the container
                binding.roomContainer.addView(roomBinding.root)
            }
        })

        // Optional: Call this method to set test room types
        setTestRoomTypes()

        binding.backToHome.setOnClickListener {
            finish() // Close activity to return to the previous screen
        }
    }

    private fun setTestRoomTypes() {
        // Set some test room types (you can call this when you need to initialize data)
        val testRoomTypes = listOf(
            RoomType("Type A", 100.0f),
            RoomType("Type B", 150.0f),
            RoomType("Type C", 200.0f)
        )
        viewModel.setRoomTypes(testRoomTypes)
    }
}
