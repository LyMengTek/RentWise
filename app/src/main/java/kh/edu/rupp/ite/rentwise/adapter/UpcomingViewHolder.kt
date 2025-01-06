package kh.edu.rupp.ite.rentwise.adapter

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderUpcomingBinding
import kh.edu.rupp.ite.rentwise.model.Rental
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone


class UpcomingViewHolder(private val binding: ViewHolderUpcomingBinding): RecyclerView.ViewHolder(binding.root){
    fun bind(rental: Rental){
        binding.upcomingUsername.text = rental.renter.username
        binding.upcomingFloor.text = "Floor: " + rental.room.floor.toString()
        binding.upcomingRoom.text = "Room: " + rental.room.room_number
        binding.upcomingDate.text = formatDate(rental.end_date)
        Picasso.get()
            .load(rental.renter.profile_picture)
            .into(binding.upcomingProfile)
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