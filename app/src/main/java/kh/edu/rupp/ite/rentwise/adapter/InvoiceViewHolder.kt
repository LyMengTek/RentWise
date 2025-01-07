package kh.edu.rupp.ite.rentwise.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderInvoiceBinding
import kh.edu.rupp.ite.rentwise.model.Invoice
import java.text.SimpleDateFormat
import java.util.*

class InvoiceViewHolder(private val binding: ViewHolderInvoiceBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(invoice: Invoice) {
        binding.inUsername.text = invoice.rental.renter.username
        binding.inFloor.text = "Floor: " + invoice.rental.room.floor.toString()
        binding.inRoom.text = "Room: " + invoice.rental.room.room_number
        binding.inDate.text = formatDate(invoice.created_at)

        // Load renter's profile picture using Picasso
        Picasso.get().load(invoice.rental.renter.profile_picture)
            .into(binding.inProfile)

        binding.inUseElectricity.text = invoice.rental.utility_usage.electricity_usage
        binding.inUseWater.text = invoice.rental.utility_usage.water_usage
        binding.inUseOther.text = invoice.rental.utility_usage.other

        binding.inCostElectricity.text = invoice.rental.room.utility_price.electricity_price
        binding.inCostWater.text = invoice.rental.room.utility_price.water_price
//        binding.inCostOther.text = invoice.rental.room.utility_price.

        binding.inTotal.text = invoice.amount_due
    }

    private fun formatDate(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        return try {
            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate!!)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}
