package kh.edu.rupp.ite.rentwise.adapter

import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderUpcomingBinding
import kh.edu.rupp.ite.rentwise.model.Invoice

class UpcomingViewHolder(private val binding: ViewHolderUpcomingBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(invoice: Invoice){
        binding.upcomingUsername.text = invoice.user.username
    }
}