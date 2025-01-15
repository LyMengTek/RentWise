package kh.edu.rupp.ite.rentwise.adapter

import RoomLocation
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.adapter.SetupRoom.AssignRoomViewHolder
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding
import kh.edu.rupp.ite.rentwise.model.RoomData
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignRoomsRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RentalRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.Room
import kh.edu.rupp.ite.rentwise.model.setuproom.response.RoomAssignmentViewModel


class AssignRoomAdapter(
    private val roomTypes: List<String>, // Room types fetched from the backend
    private val onRoomDataChanged: (RoomData) -> Unit
) : RecyclerView.Adapter<AssignRoomViewHolder>() {

    private var rooms: List<Room> = emptyList()

    fun updateRooms(newRooms: List<Room>) {
        rooms = newRooms
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignRoomViewHolder {
        val binding = ViewHolderRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AssignRoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AssignRoomViewHolder, position: Int) {
        holder.bind(rooms[position], roomTypes, onRoomDataChanged)
    }

    override fun getItemCount(): Int = rooms.size
}