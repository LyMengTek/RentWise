package kh.edu.rupp.ite.rentwise.adapter

import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderBillingBinding
import kh.edu.rupp.ite.rentwise.model.Invoice

class BillingViewHolder(private val binding: ViewHolderBillingBinding) : RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(invoice: Invoice) {
        binding.calName.text = invoice.user.username
    }
}