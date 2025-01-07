package kh.edu.rupp.ite.rentwise.adapter

import RoomLocation
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.adapter.SetupRoom.AssignRoomViewHolder
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RentalRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.response.RoomAssignmentViewModel


class AssignRoomAdapter(
    private val viewModel: RoomAssignmentViewModel
) : RecyclerView.Adapter<AssignRoomViewHolder>() {
    private var rooms: List<RoomLocation> = emptyList()
    private var roomTypes: List<String> = emptyList()

    fun updateRooms(newRooms: List<RoomLocation>) {
        rooms = newRooms
        notifyDataSetChanged()
    }

    fun updateRoomTypes(newTypes: List<String>) {
        roomTypes = newTypes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignRoomViewHolder {
        val binding = ViewHolderRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AssignRoomViewHolder(
            binding = binding,
            roomId = rooms.getOrNull(viewType)?.id ?: "",
            viewModel = viewModel
        )
    }

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: AssignRoomViewHolder, position: Int) {
        val room = rooms.getOrNull(position) ?: return
        holder.bind(room, roomTypes)
    }

    override fun getItemCount(): Int = rooms.size
}