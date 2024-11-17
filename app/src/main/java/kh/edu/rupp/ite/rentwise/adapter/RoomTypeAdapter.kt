package kh.edu.rupp.ite.rentwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.RoomTypeItemBinding
import kh.edu.rupp.ite.rentwise.viewmodel.RoomType

class RoomTypeAdapter(private val roomTypes: List<RoomType>) : RecyclerView.Adapter<RoomTypeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomTypeViewHolder {
        val binding = RoomTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomTypeViewHolder, position: Int) {
        holder.bind(roomTypes[position], position)
    }

    override fun getItemCount(): Int = roomTypes.size
}
