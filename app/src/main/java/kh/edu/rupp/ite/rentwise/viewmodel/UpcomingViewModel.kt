package kh.edu.rupp.ite.rentwise.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.Rental
import kh.edu.rupp.ite.rentwise.model.State
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UpcomingViewModel : ViewModel() {

    private val _upcomingState = MutableLiveData<ApiState<List<Rental>>>()
    val upcomingState: LiveData<ApiState<List<Rental>>> get() = _upcomingState

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchUpcomingRooms(landlordId: Int) {
        _upcomingState.value = ApiState(State.loading, null)

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getRental(landlordId)

                if (response.status == "success") {
                    val currentDate = LocalDate.now()
                    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

                    val filteredRentals = response.data?.filter { rental ->
                        val endDate = LocalDate.parse(rental.end_date, formatter)
                        rental.is_active && endDate.isAfter(currentDate)
                    }
                    _upcomingState.postValue(ApiState(State.success, filteredRentals))
                } else {
                    _upcomingState.postValue(ApiState(State.error, null))
                }
            } catch (exception: Exception) {
                _upcomingState.postValue(ApiState(State.error, null))
            }
        }
    }
}
