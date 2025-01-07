package kh.edu.rupp.ite.rentwise.model.setuproom.respone

import kh.edu.rupp.ite.rentwise.model.Floor
import kh.edu.rupp.ite.rentwise.model.setuproom.request.RoomType

// Update SetupStatusResponse.kt
data class SetupStatusResponse(
    val success: Boolean,
    val data: ConfigurationData?,
    val error: String? = null,
    val message: String? = null
)

data class ConfigurationData(
    val room_types: List<String>,
    val rooms: List<RoomLocation>
)

data class RoomLocation(
    val floor: Int,
    val room: Int
)