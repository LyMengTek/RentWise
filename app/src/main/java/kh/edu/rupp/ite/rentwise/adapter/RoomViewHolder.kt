package kh.edu.rupp.ite.rentwise.adapter

import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding

class RoomViewHolder(private val binding: ViewHolderRoomBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(floor: Int, roomCount: Int) {
        binding.floorLabel.text = "Floor: $floor"
        binding.RoomLabel.text = "Rooms: $roomCount"
    }
}