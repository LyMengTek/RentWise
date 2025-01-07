// In your response data classes file, update to match the exact API response structure:

data class LandlordConfigurationsResponse(
    val success: Boolean,
    val data: ConfigurationData
)

data class ConfigurationData(
    val room_types: List<String>,
    val rooms: List<RoomLocation>
)

// Renamed from Room to RoomLocation to avoid conflicts with Android's Room
data class RoomLocation(
    val id: String,
    val floor: Int,
    val room: Int,
    val type: String
)