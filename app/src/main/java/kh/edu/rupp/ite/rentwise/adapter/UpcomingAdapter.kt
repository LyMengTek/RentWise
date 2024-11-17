package kh.edu.rupp.ite.rentwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderUpcomingBinding
import kh.edu.rupp.ite.rentwise.model.Invoice

class UpcomingAdapter(private var invoices: List<Invoice>): RecyclerView.Adapter<UpcomingViewHolder>(){
    fun setInvoice(invoices: List<Invoice>) {
        this.invoices = invoices
        notifyDataSetChanged() // Notify the adapter to refresh the UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderUpcomingBinding.inflate(inflater, parent, false)
        return UpcomingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return invoices.size
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val invoice = invoices[position]
        holder.bind(invoice)
    }
}