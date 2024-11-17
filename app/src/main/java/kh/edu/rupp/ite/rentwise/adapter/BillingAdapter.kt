package kh.edu.rupp.ite.rentwise.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderBillingBinding
import kh.edu.rupp.ite.rentwise.model.Invoice

class BillingAdapter(private var invoices: List<Invoice>) : RecyclerView.Adapter<BillingViewHolder>() {

    // Update the data in the adapter
    fun setInvoice(invoices: List<Invoice>) {
        this.invoices = invoices
        notifyDataSetChanged() // Notify the adapter to refresh the UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderBillingBinding.inflate(inflater, parent, false)
        return BillingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillingViewHolder, position: Int) {
        val invoice = invoices[position]
        Log.d("BillingAdapter", "Binding item at position: $position with username: ${invoice.user.username}")
        holder.bind(invoice) // Binding the data
    }

    override fun getItemCount(): Int {
        val count = invoices.size
        Log.d("BillingAdapter", "Item count: $count") // Log the item count
        return count
    }
}