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
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.viewmodel.BillingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BillingFragment : Fragment() {

    private val viewModel = BillingViewModel()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and Adapter
        setupRecyclerView()

        viewModel.dueRoomState.observe(viewLifecycleOwner){ dueRoomState ->
            when(dueRoomState.state){
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    displayDueRoom(dueRoomState.data!!)
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
            }
        }
        viewModel.loadDueRoom()
    }

    private fun setupRecyclerView() {
        billingAdapter = BillingAdapter(listOf()) // Start with an empty list
        binding.billingRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.billingRecyclerview.adapter = billingAdapter
    }

    private fun displayDueRoom(dueRoom: List<Invoice>){
        billingAdapter.setInvoice(dueRoom)
    }

    private fun showLoading() {
        binding.billingRecyclerview.visibility = View.GONE
        binding.billProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.billProgressBar.visibility = View.GONE
        binding.billingRecyclerview.visibility = View.VISIBLE
    }

    private fun showErrorContent() {
        binding.billingRecyclerview.visibility = View.GONE
        binding.billError.visibility = View.VISIBLE
    }

}