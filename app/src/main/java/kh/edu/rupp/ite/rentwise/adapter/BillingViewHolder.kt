package kh.edu.rupp.ite.rentwise.adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderBillingBinding
import kh.edu.rupp.ite.rentwise.model.Rental
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

class BillingViewHolder(private val binding: ViewHolderBillingBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bind(rental: Rental, onSubmit: (Rental, Int, Int, Int) -> Unit) {
        // Set text values
        binding.calName.text = rental.renter.username
        binding.calFloor.text = "Floor: " + rental.room.floor.toString()
        binding.calRoom.text = "Room: " + rental.room.room_number
        binding.calDate.text = formatDate(rental.end_date)

        // Load image using Picasso
        Picasso.get().load(rental.renter.profile_picture).into(binding.calProfile)

        // Set up the button click listener
        binding.calBtn.setOnClickListener {
            // Retrieve input values
            val waterUsage = binding.calWaterInput.text.toString().toIntOrNull() ?: 0
            val electricityUsage = binding.calElectricityInput.text.toString().toIntOrNull() ?: 0
            val otherCost = binding.calOtherInput.text.toString().toIntOrNull() ?: 0

            rental.is_active = false

            // Call the callback function with the rental and input values
            onSubmit(rental, waterUsage, electricityUsage, otherCost)
        }
    }

    fun formatDate(inputDate: String): String {
        // Define the input and output date formats
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC") // Set the input to UTC

        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

        return try {
            val date = inputFormat.parse(inputDate) // Parse the input date string
            outputFormat.format(date!!) // Format to the desired output
        } catch (e: Exception) {
            e.printStackTrace()
            "Invalid date"
        }
    }
}

