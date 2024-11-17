package kh.edu.rupp.ite.rentwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding

class RoomAdapter(private var roomData: List<Pair<Int, Int>>) : RecyclerView.Adapter<RoomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
        val binding = ViewHolderRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomViewHolder, position: Int) {
        val (floor, roomCount) = roomData[position]
        holder.bind(floor, roomCount)
    }

    override fun getItemCount(): Int = roomData.size

    fun updateData(newData: List<Pair<Int, Int>>) {
        roomData = newData
        notifyDataSetChanged() // Notify the adapter to refresh the data
    }
}
