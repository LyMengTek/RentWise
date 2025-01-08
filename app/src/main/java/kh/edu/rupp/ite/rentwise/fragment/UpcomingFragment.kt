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
import kh.edu.rupp.ite.rentwise.adapter.Billing.UpcomingAdapter
import kh.edu.rupp.ite.rentwise.databinding.FragmentUpcomingBinding
import kh.edu.rupp.ite.rentwise.model.Rental
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.viewmodel.UpcomingViewModel

class UpcomingFragment : Fragment() {

    private val viewModel: UpcomingViewModel by viewModels()
    private lateinit var binding: FragmentUpcomingBinding
    private lateinit var upcomingAdapter: UpcomingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false)
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
            Log.e("UpcomingFragment", "Landlord ID not found in SharedPreferences")
            showErrorContent()
            return
        }

        // Observe LiveData
        viewModel.upcomingState.observe(viewLifecycleOwner) { upcomingState ->
            when (upcomingState.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    upcomingState.data?.let {
                        displayUpcomingRooms(it)
                    }
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
            }
        }

        // Fetch upcoming data
        viewModel.fetchUpcomingRooms(landlordId)
    }

    private fun setupRecyclerView() {
        upcomingAdapter = UpcomingAdapter(listOf())
        binding.upcomingRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.upcomingRecyclerview.adapter = upcomingAdapter
    }

    private fun displayUpcomingRooms(rentals: List<Rental>) {
        upcomingAdapter.setRentals(rentals)
    }

    private fun showLoading() {
        binding.upcomingRecyclerview.visibility = View.GONE
        binding.upcomingProgressBar.visibility = View.VISIBLE
        binding.upcomingError.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.upcomingProgressBar.visibility = View.GONE
        binding.upcomingRecyclerview.visibility = View.VISIBLE
    }

    private fun showErrorContent() {
        binding.upcomingError.visibility = View.VISIBLE
        binding.upcomingRecyclerview.visibility = View.GONE
        binding.upcomingProgressBar.visibility = View.GONE
    }

    private fun getLandlordIdFromPreferences(): Int? {
        val sharedPreferences = requireContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)?.toIntOrNull()
    }
}
