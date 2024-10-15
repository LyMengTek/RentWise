package kh.edu.rupp.ite.rentwise.adapter

import androidx.recyclerview.widget.RecyclerView
import kh.edu.rupp.ite.rentwise.databinding.ViewHolderContactBinding
import kh.edu.rupp.ite.rentwise.model.User

class ContactViewHolder(private val binding: ViewHolderContactBinding): RecyclerView.ViewHolder(
    binding.root
) {
    fun bind(user: User){
        binding.contactUsername.text = user.username
    }
}