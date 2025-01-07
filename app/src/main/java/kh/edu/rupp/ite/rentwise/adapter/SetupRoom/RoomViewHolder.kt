
package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding

class RoomViewHolder(private val binding: ViewHolderRoomBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(floorNumber: Int, roomNumber: Int) {
        binding.floorLabel.text = "Floor: $floorNumber"
        binding.RoomLabel.text = "Room: $roomNumber"
    }
}