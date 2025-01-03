package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.FloorItemBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.Floor

class FloorAdapter(private val floors: List<Floor>) : RecyclerView.Adapter<FloorViewHolder>() {
    private val roomCounts = mutableMapOf<Int, Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val binding = FloorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FloorViewHolder(binding) { floorNumber, roomCount ->
            roomCounts[floorNumber] = roomCount
            Log.d("FloorAdapter", "Floor $floorNumber updated with $roomCount rooms")
            Log.d("FloorAdapter", "Current room counts: $roomCounts")
        }
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        val floorNumber = position + 1
        val currentRoomCount = roomCounts[position] ?: 0
        holder.bind(floorNumber, currentRoomCount)
    }

    override fun getItemCount(): Int = floors.size

    fun getRoomCountForFloor(floorNumber: Int): Int {
        return roomCounts[floorNumber - 1] ?: 0
    }

    fun getFloorsWithRoomCounts(): List<Pair<Int, Int>> {
        return floors.mapIndexed { index, _ ->
            Pair(index + 1, roomCounts[index] ?: 0)
        }
    }
}


















