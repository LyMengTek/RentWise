package kh.edu.rupp.ite.rentwise.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.adapter.Billing.BillingAdapter
import kh.edu.rupp.ite.rentwise.databinding.FragmentBillingBinding
import kh.edu.rupp.ite.rentwise.model.Rental
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.viewmodel.BillingViewModel

class BillingFragment : Fragment() {

    private val viewModel: BillingViewModel by viewModels()
    private lateinit var binding: FragmentBillingBinding
    private lateinit var billingAdapter: BillingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBillingBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        setupRecyclerView()

        // Retrieve landlordId from SharedPreferences
        val landlordId = getLandlordIdFromPreferences()
        if (landlordId == null) {
            Log.e("BillingFragment", "Landlord ID not found in SharedPreferences")
            showErrorContent()
            return
        }

        // Observe LiveData
        viewModel.billingState.observe(viewLifecycleOwner) { billingState ->
            when (billingState.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    billingState.data?.let {
                        displayBillingRooms(it)
                    }
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
            }
        }

        // Fetch billing data
        viewModel.fetchBillingRooms(landlordId)


    }

    private fun setupRecyclerView() {
        billingAdapter = BillingAdapter(listOf()) { rental, waterUsage, electricityUsage, otherCost ->
            // Mark rental as inactive
            rental.is_active = false

            // Update the adapter to reflect the change
            billingAdapter.setRentals(billingAdapter.getRentals()) // Use getRentals() to access rentals

            // Log and create the invoice
            Log.d("BillingFragment", "Submit for rental ID: ${rental.id}, Water: $waterUsage, Electricity: $electricityUsage, Other Cost: $otherCost")
            viewModel.createInvoice(rental, waterUsage, electricityUsage, otherCost)
        }

        binding.billingRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.billingRecyclerview.adapter = billingAdapter
    }


    private fun displayBillingRooms(rentals: List<Rental>) {
        billingAdapter.setRentals(rentals)
    }

    private fun showLoading() {
        binding.billingRecyclerview.visibility = View.GONE
        binding.billProgressBar.visibility = View.VISIBLE
        binding.billError.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.billProgressBar.visibility = View.GONE
        binding.billingRecyclerview.visibility = View.VISIBLE
    }

    private fun showErrorContent() {
        binding.billError.visibility = View.VISIBLE
        binding.billingRecyclerview.visibility = View.GONE
        binding.billProgressBar.visibility = View.GONE
    }

    private fun getLandlordIdFromPreferences(): Int? {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)?.toIntOrNull()
    }
}
