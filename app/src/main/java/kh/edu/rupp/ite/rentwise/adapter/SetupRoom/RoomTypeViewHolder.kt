
package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.RoomTypeItemBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.RoomType

class RoomTypeViewHolder(private val binding: RoomTypeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(roomType: RoomType, position: Int) {
        Log.d("RoomTypeViewHolder", "Binding room type: ${roomType.name}, ${roomType.price}")
        binding.roomTypeLabel.text = "Room Type ${position + 1}"
        binding.roomTypeName.setText(roomType.name) // Set initial room type name
        binding.roomTypePrice.setText(roomType.price.toString()) // Set initial room type price

        // Listen for changes in the room type name
        binding.roomTypeName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                roomType.name = s.toString() // Update the room type name
            }
        })

        // Listen for changes in the room type price
        binding.roomTypePrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                roomType.price = s.toString().toDoubleOrNull() ?: 0.0 // Update the room type price
            }
        })
    }
}