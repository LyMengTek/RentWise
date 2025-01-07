package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.FloorItemBinding

class FloorViewHolder(
    private val binding: FloorItemBinding,
    private val onRoomCountChanged: (floorNumber: Int, roomCount: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(floorNumber: Int, roomCount: Int) {
        // Using the correct IDs from your layout
        binding.floorLabel.text = "Floor $floorNumber"
        binding.numberOfRoom.setText(roomCount.toString())

        binding.numberOfRoom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val newRoomCount = s.toString().toIntOrNull() ?: 0
                onRoomCountChanged(floorNumber, newRoomCount)
            }
        })
    }
}