package kh.edu.rupp.ite.rentwise.adapter.SetupRoom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.RoomTypeItemBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RoomType

class RoomTypeAdapter(
    initialRoomTypes: List<RoomType>,
    private val onRoomTypeSelected: (RoomType) -> Unit
) : RecyclerView.Adapter<RoomTypeViewHolder>() {

    // Use MutableList internally
    private val _roomTypes = initialRoomTypes.toMutableList()

    // Expose roomTypes as immutable List for external use
    val roomTypes: List<RoomType>
        get() = _roomTypes.toList()

    fun updateRoomTypes(newRoomTypes: List<RoomType>) {
        _roomTypes.clear()
        _roomTypes.addAll(newRoomTypes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomTypeViewHolder {
        val binding = RoomTypeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RoomTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomTypeViewHolder, position: Int) {
        val roomType = _roomTypes[position]
        holder.bind(roomType, position) { updatedType ->
            _roomTypes[position] = updatedType
            onRoomTypeSelected(updatedType)
        }
    }

    override fun getItemCount(): Int = _roomTypes.size
}