package kh.edu.rupp.ite.rentwise.model.setuproom.respone

data class SetupStatusResponse(
    val isSetupComplete: Boolean,
    val roomTypes: List<RoomType>,
    val floors: List<Floor>,
    val utilityPrices: UtilityPrices
)


data class RoomType(
    var name: String,
    var price: Double
)

data class Floor(
    val floorNumber: Int,
    val room: List<Int>
)

data class UtilityPrices(
    val waterPrice: String,
    val electricityPrice: String
)
