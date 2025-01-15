package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import RoomLocation
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding
import kh.edu.rupp.ite.rentwise.model.RoomData
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.Room
import kh.edu.rupp.ite.rentwise.model.setuproom.response.RoomAssignmentViewModel

class AssignRoomViewHolder(
    private val binding: ViewHolderRoomBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        room: Room,
        roomTypes: List<String>,
        onRoomDataChanged: (RoomData) -> Unit
    ) {
        binding.floorLabel.text = "Floor ${room.floor}"
        binding.RoomLabel.text = "Room ${room.room}"

        // Create adapter with the exact room types from the API
        val adapter = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_spinner_item,
            roomTypes
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.roomTypeSpinner.adapter = adapter

        // Select first room type by default if list is not empty
        if (roomTypes.isNotEmpty()) {
            binding.roomTypeSpinner.setSelection(0)
        }

        // Setup change listeners
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                updateRoomData(onRoomDataChanged)
            }
        }

        binding.electricityUsageInput.addTextChangedListener(textWatcher)
        binding.waterUsageInput.addTextChangedListener(textWatcher)
        binding.description.addTextChangedListener(textWatcher)
        binding.tenentcode.addTextChangedListener(textWatcher)

        binding.roomTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateRoomData(onRoomDataChanged)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun updateRoomData(onRoomDataChanged: (RoomData) -> Unit) {
        val roomData = RoomData(
            floor = binding.floorLabel.text.toString().filter { it.isDigit() }.toIntOrNull() ?: 0,
            room = binding.RoomLabel.text.toString().filter { it.isDigit() }.toIntOrNull() ?: 0,
            electricity = binding.electricityUsageInput.text.toString().toFloatOrNull() ?: 0f,
            water = binding.waterUsageInput.text.toString().toFloatOrNull() ?: 0f,
            description = binding.description.text.toString(),
            roomType = binding.roomTypeSpinner.selectedItem?.toString()?.toLowerCase() ?: "",
            tenantCode = binding.tenentcode.text.toString()
        )
        onRoomDataChanged(roomData)
    }
}