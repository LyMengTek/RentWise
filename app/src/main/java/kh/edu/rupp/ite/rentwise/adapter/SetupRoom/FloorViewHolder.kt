package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.FloorItemBinding

class FloorViewHolder(
    val binding: FloorItemBinding,
    private val onRoomCountChanged: (floorNumber: Int, roomCount: Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.numberOfRoom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val roomCount = s.toString().toIntOrNull() ?: 0
                onRoomCountChanged(adapterPosition, roomCount)
            }
        })
    }

    fun bind(floorNumber: Int, roomCount: Int) {
        binding.floorLabel.text = "Floor $floorNumber"
        binding.numberOfRoom.hint = "Enter number of rooms for floor $floorNumber"
        if (binding.numberOfRoom.text.toString().isEmpty()) {
            binding.numberOfRoom.setText(roomCount.toString())
        }
    }
}