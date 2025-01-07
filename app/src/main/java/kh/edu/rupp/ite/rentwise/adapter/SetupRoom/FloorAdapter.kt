package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.FloorItemBinding
import kh.edu.rupp.ite.rentwise.model.Floor

class FloorAdapter(private var floors: List<Floor>) : RecyclerView.Adapter<FloorViewHolder>() {
    private val roomCounts = mutableMapOf<Int, Int>()

    init {
        floors.forEach { floor ->
            roomCounts[floor.floor_number] = floor.room_count
        }
    }

    fun updateFloors(newFloors: List<Floor>) {
        floors = newFloors.sortedBy { it.floor_number }
        roomCounts.clear()
        newFloors.forEach { floor ->
            roomCounts[floor.floor_number] = floor.room_count
        }
        notifyDataSetChanged()
        Log.d("FloorAdapter", "Updated floors: $floors")
        Log.d("FloorAdapter", "Updated room counts: $roomCounts")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val binding = FloorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FloorViewHolder(binding) { floorNumber, roomCount ->
            roomCounts[floorNumber] = roomCount
            Log.d("FloorAdapter", "Floor $floorNumber updated with $roomCount rooms")
            Log.d("FloorAdapter", "Current room counts: $roomCounts")
        }
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        val floor = floors[position]
        val currentRoomCount = roomCounts[floor.floor_number] ?: floor.room_count
        holder.bind(floor.floor_number, currentRoomCount)
    }

    override fun getItemCount(): Int = floors.size

    fun getRoomCountForFloor(floorNumber: Int): Int {
        return roomCounts[floorNumber] ?: 0
    }

    fun getFloorsWithRoomCounts(): List<Pair<Int, Int>> {
        return floors.sortedBy { it.floor_number }.map { floor ->
            Pair(floor.floor_number, roomCounts[floor.floor_number] ?: floor.room_count)
        }
    }
}
