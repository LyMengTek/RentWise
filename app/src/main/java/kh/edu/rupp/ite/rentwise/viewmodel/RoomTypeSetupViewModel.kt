package kh.edu.rupp.ite.rentwise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class RoomType(val name: String, val price: Float)

class RoomTypeSetupViewModel : ViewModel() {
    private val _floors = MutableLiveData<Map<Int, Int>>()
    private val _roomTypes = MutableLiveData<List<RoomType>>()
    private val _electricityPrice = MutableLiveData<Float>()
    private val _waterPrice = MutableLiveData<Float>()

    val floors: LiveData<Map<Int, Int>> = _floors
    val roomTypes: LiveData<List<RoomType>> = _roomTypes

    private val _saveStatus = MutableLiveData<Boolean>() // Track save status
    val saveStatus: LiveData<Boolean> = _saveStatus

    fun setFloorRoomCount(floor: Int, roomCount: Int) {
        val currentData = _floors.value?.toMutableMap() ?: mutableMapOf()
        currentData[floor] = roomCount
        _floors.value = currentData
        _saveStatus.value = true // Set to true after saving
    }

    fun setElectricityPrice(price: Float) {
        _electricityPrice.value = price
    }

    fun setWaterPrice(price: Float) {
        _waterPrice.value = price
    }

    fun setRoomTypes(roomTypes: List<RoomType>) {
        _roomTypes.value = roomTypes
        _saveStatus.value = true // Set to true after saving
    }

    // Reset the save status after it's been handled
    fun resetSaveStatus() {
        _saveStatus.value = false
    }

}
