package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding
import kh.edu.rupp.ite.rentwise.model.Floor

class RoomAdapter(
    private var floor: Floor, // Use var to allow updates
    private val onRoomSelected: (Int) -> Unit // Callback for room selection
) : RecyclerView.Adapter<RoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ViewHolderRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val roomNumber = position + 1
        holder.bind(floor.floor_number, roomNumber)

        // Handle item click
        holder.itemView.setOnClickListener {
            onRoomSelected(roomNumber)
        }
    }

    override fun getItemCount(): Int = floor.room_count

    // Update floor data and notify adapter
    fun updateFloor(newFloor: Floor) {
        floor = newFloor
        notifyDataSetChanged()
    }
}