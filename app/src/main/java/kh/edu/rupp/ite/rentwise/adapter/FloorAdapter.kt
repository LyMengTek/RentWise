package kh.edu.rupp.ite.rentwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.FloorItemBinding

class FloorAdapter(private val floorCounts: List<Int>) : RecyclerView.Adapter<FloorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloorViewHolder {
        val binding = FloorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FloorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FloorViewHolder, position: Int) {
        holder.binding.floorLabel.text = "Floor ${position + 1}"
        holder.binding.numberOfRoom.hint = "Enter number of rooms for floor ${position + 1}"
    }

    override fun getItemCount(): Int = floorCounts.size
}
