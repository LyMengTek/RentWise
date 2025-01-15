package kh.edu.rupp.ite.rentwise.adapter.Contact

import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderContactBinding
import kh.edu.rupp.ite.rentwise.model.Contact
import kh.edu.rupp.ite.rentwise.model.User

class ContactViewHolder(private val binding: ViewHolderContactBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(contact: Contact) {
        binding.apply {
            contactUsername.text = contact.name
            contactEmail.text = contact.email
            contactPhoneNumber.text = contact.phone
            // You can add image loading logic here for profile_pic if needed
        }
    }
}