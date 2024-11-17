package kh.edu.rupp.ite.rentwise.adapter

import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.RoomTypeItemBinding
import kh.edu.rupp.ite.rentwise.viewmodel.RoomType

class RoomTypeViewHolder(private val binding: RoomTypeItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(roomType: RoomType, position: Int) {
        binding.roomTypeLabel.text = "Room Type ${position + 1}"
        binding.roomTypeName.setText(roomType.name)
        binding.roomTypePrice.setText(roomType.price.toString())
    }
}
