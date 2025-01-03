package kh.edu.rupp.ite.rentwise.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.adapter.Billing.UpcomingAdapter
import kh.edu.rupp.ite.rentwise.databinding.FragmentUpcomingBinding


class UpcomingFragment : Fragment() {

    private var _binding: FragmentUpcomingBinding? = null
    private val binding get() = _binding!!

    private lateinit var upcomingAdapter: UpcomingAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

//        lordUpcomingRoom()
    }

    private fun setupRecyclerView(){
        upcomingAdapter = UpcomingAdapter(listOf())
        binding.upcomingRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.upcomingRecyclerview.adapter = upcomingAdapter
    }

//    private fun lordUpcomingRoom(){
//        RetrofitClient.instance.getDueRoom().enqueue(object : retrofit2.Callback<List<Invoice>>{
//            override fun onResponse(call: Call<List<Invoice>>, response: Response<List<Invoice>>) {
//                if (response.isSuccessful){
//                    val invoice = response.body() ?: listOf()
//                    upcomingAdapter.setInvoice(invoice)
//                }else{
//                    Toast.makeText(
//                        requireContext(),
//                        "Error while loading data from server",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<List<Invoice>>, t: Throwable) {
//                Toast.makeText(
//                    requireContext(),
//                    "Error: ${t.message}",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//        })
//    }
}