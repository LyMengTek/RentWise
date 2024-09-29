package kh.edu.rupp.ite.rentwise.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.adapter.BillingAdapter
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.databinding.FragmentBillingBinding
import kh.edu.rupp.ite.rentwise.model.Invoice
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillingFragment : Fragment() {

    private var _binding: FragmentBillingBinding? = null
    private val binding get() = _binding!!

    private lateinit var billingAdapter: BillingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBillingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        setupRecyclerView()

        // Fetch data after the view is created
        loadDueRoom()
    }

    private fun setupRecyclerView() {
        billingAdapter = BillingAdapter(listOf()) // Start with an empty list
        binding.billingRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.billingRecyclerview.adapter = billingAdapter
    }

    private fun loadDueRoom() {
        RetrofitClient.instance.getDueRoom().enqueue(object : Callback<List<Invoice>> {
            override fun onResponse(call: Call<List<Invoice>>, response: Response<List<Invoice>>) {
                if (response.isSuccessful) {
                    val invoices = response.body() ?: listOf() // Fallback to an empty list if null
                    billingAdapter.setInvoice(invoices) // Update the adapter with the fetched data
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Error while loading data from server",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Invoice>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}