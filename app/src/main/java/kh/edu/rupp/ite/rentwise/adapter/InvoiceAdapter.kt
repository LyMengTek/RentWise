package kh.edu.rupp.ite.rentwise.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderInvoiceBinding
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.viewholder.InvoiceViewHolder

class InvoiceAdapter(private var invoices: List<Invoice>) :
    RecyclerView.Adapter<InvoiceViewHolder>() {

    fun setInvoices(invoices: List<Invoice>) {
        this.invoices = invoices
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderInvoiceBinding.inflate(inflater, parent, false)
        return InvoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
        val invoice = invoices[position]
        holder.bind(invoice) // Only bind data without click handling
    }

    override fun getItemCount(): Int = invoices.size
}
