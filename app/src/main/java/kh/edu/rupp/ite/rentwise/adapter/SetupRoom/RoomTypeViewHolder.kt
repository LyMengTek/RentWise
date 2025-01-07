package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.RoomTypeItemBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RoomType

class RoomTypeViewHolder(
    private val binding: RoomTypeItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var currentRoomType: RoomType? = null
    private var textWatcherName: TextWatcher? = null
    private var textWatcherPrice: TextWatcher? = null

    fun bind(roomType: RoomType, position: Int, onUpdate: (RoomType) -> Unit) {
        currentRoomType = roomType.copy()

        binding.roomTypeLabel.text = "Room Type ${position + 1}"
        binding.roomTypeName.setText(roomType.type)
        binding.roomTypePrice.setText(roomType.price.toString())

        // Remove previous text watchers if they exist
        textWatcherName?.let { binding.roomTypeName.removeTextChangedListener(it) }
        textWatcherPrice?.let { binding.roomTypePrice.removeTextChangedListener(it) }

        // Create and set new text watchers
        textWatcherName = createNameTextWatcher(onUpdate)
        textWatcherPrice = createPriceTextWatcher(onUpdate)

        binding.roomTypeName.addTextChangedListener(textWatcherName)
        binding.roomTypePrice.addTextChangedListener(textWatcherPrice)
    }

    private fun createNameTextWatcher(onUpdate: (RoomType) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                currentRoomType?.let { roomType ->
                    roomType.type = s.toString()
                    onUpdate(roomType)
                }
            }
        }
    }

    private fun createPriceTextWatcher(onUpdate: (RoomType) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                currentRoomType?.let { roomType ->
                    roomType.price = s.toString().toDoubleOrNull() ?: 0.0
                    onUpdate(roomType)
                }
            }
        }
    }
}