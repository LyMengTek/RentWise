package kh.edu.rupp.ite.rentwise.model.setuproom

data class FloorRoomsRequest(
    val landlord_id: Int,
    val floors: List<Floor>
) {
    data class Floor(
        val floor_number: Int,
        val room_count: Int
    )
}
