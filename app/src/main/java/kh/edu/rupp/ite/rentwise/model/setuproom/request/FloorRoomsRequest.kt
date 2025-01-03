package kh.edu.rupp.ite.rentwise.model.setuproom.request

data class FloorRoomsRequest(
    val landlord_id: Int,
    val floors: List<kh.edu.rupp.ite.rentwise.model.Floor>
) {
    data class Floor(
        val floor_number: Int,
        val room_count: Int
    )
}

