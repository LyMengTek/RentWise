package kh.edu.rupp.ite.rentwise.adapter.Billing

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderUpcomingBinding
import kh.edu.rupp.ite.rentwise.model.Rental

class UpcomingAdapter(private var rentals: List<Rental>) : RecyclerView.Adapter<UpcomingViewHolder>() {

    // Update the data in the adapter
    fun setRentals(rentals: List<Rental>) {
        this.rentals = rentals
        notifyDataSetChanged() // Notify the adapter to refresh the UI
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderUpcomingBinding.inflate(inflater, parent, false)
        return UpcomingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val rental = rentals[position]
        Log.d("UpcomingAdapter", "Binding item at position: $position with rental ID: ${rental.id}")
        holder.bind(rental) // Bind the rental data
    }

    override fun getItemCount(): Int {
        val count = rentals.size
        Log.d("UpcomingAdapter", "Item count: $count") // Log the item count
        return count
    }
}
