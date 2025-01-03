package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding

class AssignRoomViewHolder(
    private val binding: ViewHolderRoomBinding,
    private val onRoomTypeSelected: (position: Int, selectedType: String, waterUsage: Double, electricityUsage: Double) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.roomTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position) as String
                val waterUsage = binding.waterUsageInput.text.toString().toDoubleOrNull() ?: 0.0
                val electricityUsage = binding.electricityUsageInput.text.toString().toDoubleOrNull() ?: 0.0
                onRoomTypeSelected(adapterPosition, selectedType, waterUsage, electricityUsage)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun bind(floor: Int, roomNumber: Int, roomTypes: List<String>) {
        binding.floorLabel.text = "Floor: $floor"
        binding.RoomLabel.text = "Room: $roomNumber"

        val roomTypeAdapter = ArrayAdapter(
            binding.root.context,
            android.R.layout.simple_spinner_item,
            roomTypes
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.roomTypeSpinner.adapter = roomTypeAdapter
    }
}