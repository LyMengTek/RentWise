package kh.edu.rupp.ite.rentwise.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.InvoiceRequest
import kh.edu.rupp.ite.rentwise.model.Rental
import kh.edu.rupp.ite.rentwise.model.State
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class BillingViewModel : ViewModel() {

    private val _billingState = MutableLiveData<ApiState<List<Rental>>>()
    val billingState: LiveData<ApiState<List<Rental>>> get() = _billingState

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchBillingRooms(landlordId: Int) {
        _billingState.value = ApiState(State.loading, null)

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getRental(landlordId)

                if (response.status == "success") {
                    val currentDate = LocalDate.now()
                    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME

                    val filteredRentals = response.data?.filter { rental ->
                        val endDate = LocalDate.parse(rental.end_date, formatter)
                        !rental.is_active || endDate.isBefore(currentDate) || endDate.isEqual(
                            currentDate
                        )
                    }
                    _billingState.postValue(ApiState(State.success, filteredRentals))
                } else {
                    _billingState.postValue(ApiState(State.error, null))
                }
            } catch (exception: Exception) {
                _billingState.postValue(ApiState(State.error, null))
            }
        }
    }

    private val _invoiceState = MutableLiveData<ApiState<Invoice>>()
    val invoiceState: LiveData<ApiState<Invoice>> get() = _invoiceState

    fun createInvoice(rental: Rental, newWaterUsage: Int, newElectricityUsage: Int, other: Int) {
        _invoiceState.value = ApiState(State.loading, null)

        viewModelScope.launch {
            try {
                val invoiceRequest = InvoiceRequest(
                    rental_id = rental.id,
                    landlord_id = rental.landlord_id,
                    renter_id = rental.renter_id,
                    new_water_usage = newWaterUsage,
                    new_electricity_usage = newElectricityUsage,
                    other = other
                )

                val response = RetrofitClient.instance.createInvoice(invoiceRequest)

                if (response.status == "success") {
                    _invoiceState.postValue(
                        ApiState(
                            State.success,
                            response.data?.first()
                        )
                    ) // Assuming response returns a list of invoices
                } else {
                    _invoiceState.postValue(ApiState(State.error, null))
                }
            } catch (exception: Exception) {
                _invoiceState.postValue(ApiState(State.error, null))
            }
        }
    }
}