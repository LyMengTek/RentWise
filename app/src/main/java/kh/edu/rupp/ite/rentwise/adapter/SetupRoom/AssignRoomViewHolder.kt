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
import kh.edu.rupp.ite.rentwise.model.setuproom.response.RoomAssignmentViewModel

class AssignRoomViewHolder(
    private val binding: ViewHolderRoomBinding,
    private val roomId: String,
    private val viewModel: RoomAssignmentViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(room: RoomLocation, roomTypes: List<String>) {
        binding.apply {
            floorLabel.text = "Floor ${room.floor}"
            RoomLabel.text = "Room ${room.room}"

            // Setup spinner
            ArrayAdapter(
                root.context,
                android.R.layout.simple_spinner_item,
                roomTypes
            ).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                roomTypeSpinner.adapter = this
                roomTypes.indexOf(room.type).takeIf { it >= 0 }?.let {
                    roomTypeSpinner.setSelection(it)
                }
            }

            // Set up listeners
            roomTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                    updateRoomData()
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

            waterUsageInput.addTextChangedListener { updateRoomData() }
            electricityUsageInput.addTextChangedListener { updateRoomData() }
            tenentcode.addTextChangedListener { updateRoomData() }
            description.addTextChangedListener { updateRoomData() }
        }
    }

    private fun updateRoomData() {
        binding.apply {
            val type = roomTypeSpinner.selectedItem?.toString() ?: return
            val water = waterUsageInput.text.toString().toDoubleOrNull() ?: 0.0
            val electricity = electricityUsageInput.text.toString().toDoubleOrNull() ?: 0.0
            val tenant = tenentcode.text.toString()
            val desc = description.text.toString()

            viewModel.updateRoomAssignment(
                roomId = roomId,
                type = type,
                waterUsage = water,
                electricityUsage = electricity,
                tenantCode = tenant,
                description = desc
            )
        }
    }
}