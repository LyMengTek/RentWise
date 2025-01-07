package kh.edu.rupp.ite.rentwise.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.State
import kotlinx.coroutines.launch

class InvoiceViewModel : ViewModel() {

    private val _invoiceState = MutableLiveData<ApiState<List<Invoice>>>()
    val invoiceState: LiveData<ApiState<List<Invoice>>> get() = _invoiceState

    fun fetchInvoices(landlordId: Int) {
        _invoiceState.value = ApiState(State.loading, null)

        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getInvoicesByLandlord(landlordId)
                if (response.status == "success") {
                    _invoiceState.postValue(ApiState(State.success, response.data))
                } else {
                    _invoiceState.postValue(ApiState(State.error, null))
                }
            } catch (exception: Exception) {
                _invoiceState.postValue(ApiState(State.error, null))
            }
        }
    }
}
