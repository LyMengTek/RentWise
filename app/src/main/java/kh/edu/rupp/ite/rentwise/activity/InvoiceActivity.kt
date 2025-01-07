package kh.edu.rupp.ite.rentwise.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.activity.login_register.LandlordActivity
import kh.edu.rupp.ite.rentwise.adapter.InvoiceAdapter
import kh.edu.rupp.ite.rentwise.databinding.ActivityInvoiceBinding
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.viewmodel.InvoiceViewModel

class InvoiceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInvoiceBinding
    private val viewModel: InvoiceViewModel by viewModels()
    private lateinit var invoiceAdapter: InvoiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInvoiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize RecyclerView and Adapter
        setupRecyclerView()

        // Retrieve landlordId from SharedPreferences
        val landlordId = getLandlordIdFromPreferences()
        if (landlordId == null) {
            Log.e("InvoiceActivity", "Landlord ID not found in SharedPreferences")
            showErrorContent()
            return
        }

        binding.backAccount.setOnClickListener {
            val intent = Intent(this, LandlordActivity::class.java)
            startActivity(intent)
        }

        // Observe LiveData
        viewModel.invoiceState.observe(this) { state ->
            when (state.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    state.data?.let { displayInvoices(it) }
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
            }
        }

        // Fetch invoice data
        viewModel.fetchInvoices(landlordId)
    }

    private fun setupRecyclerView() {
        invoiceAdapter = InvoiceAdapter(listOf())

        binding.invoiceRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.invoiceRecyclerview.adapter = invoiceAdapter
    }

    private fun displayInvoices(invoices: List<Invoice>) {
        if (invoices.isEmpty()) {
            showErrorContent()
        } else {
            invoiceAdapter.setInvoices(invoices)
            binding.invoiceRecyclerview.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        binding.invoiceRecyclerview.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.error.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.invoiceRecyclerview.visibility = View.VISIBLE
    }

    private fun showErrorContent() {
        binding.invoiceRecyclerview.visibility = View.GONE
        binding.error.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun getLandlordIdFromPreferences(): Int? {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)?.toIntOrNull()
    }
}
