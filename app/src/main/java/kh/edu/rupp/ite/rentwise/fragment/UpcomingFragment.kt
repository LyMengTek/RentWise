package kh.edu.rupp.ite.rentwise.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.adapter.UpcomingAdapter
import kh.edu.rupp.ite.rentwise.databinding.FragmentUpcomingBinding
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.viewmodel.UpcomingViewModel


class UpcomingFragment : Fragment() {

    private val viewModel = UpcomingViewModel()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.upcomingState.observe(viewLifecycleOwner) { upcomingState ->
            when(upcomingState.state){
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    displayUpcomingRoom(upcomingState.data!!)
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
            }
        }

        viewModel.lordUpcomingRoom()
    }

    private fun setupRecyclerView() {
        upcomingAdapter = UpcomingAdapter(listOf())
        binding.upcomingRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.upcomingRecyclerview.adapter = upcomingAdapter
    }

    private fun displayUpcomingRoom(upcomingRoom: List<Invoice>){
        upcomingAdapter.setInvoice(upcomingRoom)
    }

    private fun showLoading() {
        binding.upcomingRecyclerview.visibility = View.GONE
        binding.upcomingProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.upcomingProgressBar.visibility = View.GONE
        binding.upcomingRecyclerview.visibility = View.VISIBLE
    }

    private fun showErrorContent() {
        binding.upcomingRecyclerview.visibility = View.GONE
        binding.upcomingError.visibility = View.VISIBLE
    }
}