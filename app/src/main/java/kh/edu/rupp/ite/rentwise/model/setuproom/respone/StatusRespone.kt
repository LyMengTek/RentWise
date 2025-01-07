package kh.edu.rupp.ite.rentwise.model.setuproom.response

import RoomLocation

data class SetupStatusResponse(
    val success: Boolean,
    val message: String?,
    val error: String?,
    val data: ConfigurationData
)

data class ConfigurationData(
    val room_types: List<String>,
    val rooms: List<RoomLocation>
)

data class LandlordConfigurationsResponse(
    val success: Boolean,
    val data: ConfigurationData
)