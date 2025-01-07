package kh.edu.rupp.ite.rentwise.adapter.Billing

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderBillingBinding
import kh.edu.rupp.ite.rentwise.model.Rental

class BillingAdapter(private var rentals: List<Rental>, private val onSubmit: (Rental, Int, Int, Int) -> Unit) : RecyclerView.Adapter<BillingViewHolder>() {

    fun getRentals(): List<Rental> = rentals

    // Update the data in the adapter
    fun setRentals(rentals: List<Rental>) {
        this.rentals = rentals.filter { it.is_active } // Exclude inactive rentals
        notifyDataSetChanged() // Notify the adapter to refresh the UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderBillingBinding.inflate(inflater, parent, false)
        return BillingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BillingViewHolder, position: Int) {
        val rental = rentals[position]
        Log.d("BillingAdapter", "Binding item at position: $position with rental ID: ${rental.id}")
        holder.bind(rental, onSubmit) // Bind the rental data
    }

    override fun getItemCount(): Int {
        val count = rentals.size
        Log.d("BillingAdapter", "Item count: $count") // Log the item count
        return count
    }
}
