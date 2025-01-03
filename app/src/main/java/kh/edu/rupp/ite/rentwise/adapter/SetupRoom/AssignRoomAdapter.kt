package kh.edu.rupp.ite.rentwise.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.adapter.SetupRoom.AssignRoomViewHolder
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderRoomBinding
import kh.edu.rupp.ite.rentwise.model.setuproom.request.AssignRoomRequest
import kh.edu.rupp.ite.rentwise.model.setuproom.respone.Floor

class AssignRoomAdapter : RecyclerView.Adapter<AssignRoomViewHolder>() {
    private var floors: List<Floor> = emptyList()
    private var roomTypes: List<String> = emptyList()
    private var roomDisplays: List<RoomDisplay> = emptyList()
    private val selectedRoomTypes = mutableMapOf<Int, Pair<String, Pair<Double, Double>>>()

    data class RoomDisplay(
        val floorNumber: Int,
        val roomNumber: Int,
        val displayRoomNumber: Int  // For display purposes
    )

    fun updateData(newFloors: List<Floor>, newRoomTypes: List<String>) {
        floors = newFloors
        roomTypes = newRoomTypes
        updateRoomDisplays()
        notifyDataSetChanged()
    }

    fun updateRoomTypes(newRoomTypes: List<String>) {
        roomTypes = newRoomTypes
        notifyDataSetChanged()
    }

    private fun updateRoomDisplays() {
        var globalRoomNumber = 1 // Start with room number 1
        roomDisplays = floors.flatMap { floor ->
            (1..floor.room[0]).map { roomIndex ->
                RoomDisplay(
                    floorNumber = floor.floorNumber,
                    roomNumber = globalRoomNumber++, // Use and increment global room number
                    displayRoomNumber = roomIndex // For display purposes only
                )
            }
        }
        Log.d("AssignRoomAdapter", "Floor data: $floors")
        Log.d("AssignRoomAdapter", "Generated RoomDisplays: $roomDisplays")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignRoomViewHolder {
        val binding = ViewHolderRoomBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AssignRoomViewHolder(binding) { position, selectedType, waterUsage, electricityUsage ->
            selectedRoomTypes[position] = Pair(selectedType, Pair(waterUsage, electricityUsage))
            Log.d("AssignRoomAdapter", "Selected room type at position $position: $selectedType, Water: $waterUsage, Electricity: $electricityUsage")
        }
    }

    override fun onBindViewHolder(holder: AssignRoomViewHolder, position: Int) {
        val display = roomDisplays[position]
        // Use displayRoomNumber for showing to user
        holder.bind(display.floorNumber, display.displayRoomNumber, roomTypes)
        Log.d("AssignRoomAdapter",
            "Binding position $position: Floor ${display.floorNumber}, " +
                    "Display Room ${display.displayRoomNumber}, Actual Room ${display.roomNumber}")
    }

    override fun getItemCount(): Int = roomDisplays.size

    fun getSelectedRooms(landlordId: Int, renterId: Int, utilityPriceId: Int): List<AssignRoomRequest> {
        val validRequests = mutableListOf<AssignRoomRequest>()

        selectedRoomTypes.forEach { (position, data) ->
            if (position < roomDisplays.size) {
                val display = roomDisplays[position]
                val (roomType, usagePair) = data
                val (waterUsage, electricityUsage) = usagePair

                val request = AssignRoomRequest(
                    landlord_id = landlordId,
                    renter_id = renterId,
                    floor = display.floorNumber,
                    room_number = display.roomNumber,
                    water_usage = waterUsage,
                    electricity_usage = electricityUsage,
                    room_type = roomType,
                    utility_price_id = utilityPriceId,
                    description = "Room ${display.displayRoomNumber} on floor ${display.floorNumber}"
                )

                validRequests.add(request)
                Log.d("AssignRoomAdapter",
                    "Created request for floor ${display.floorNumber}, " +
                            "room ${display.roomNumber} (display: ${display.displayRoomNumber}): $request")
            }
        }

        Log.d("AssignRoomAdapter", "Total valid requests: ${validRequests.size}")
        return validRequests
    }
}