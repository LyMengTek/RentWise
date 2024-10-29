package kh.edu.rupp.ite.rentwise.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.R
import kh.edu.rupp.ite.rentwise.adapter.ContactAdapter
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.databinding.ActivityContactBinding
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

class ContactActivity : AppCompatActivity() {
    private val viewModel = ContactViewModel()

    private lateinit var binding: ActivityContactBinding
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadUserContact() // Call to load user data on activity start

        viewModel.dueContactState.observe(this) { dueRoomState ->
            when (dueRoomState.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    // Wrap data in a list if dueRoomState.data is not null
                    displayDueRoom(listOf(dueRoomState.data!!))
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
            }
        }

        binding.backToHome.setOnClickListener {
            val intent = Intent(this, LandlordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter(listOf()) // Start with an empty list
        binding.contactRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.contactRecyclerview.adapter = contactAdapter
    }

    private fun loadUserContact() {
        lifecycleScope.launch {
            try {
                // Retrieve user data from API
                val user = RetrofitClient.instance.getUser()
                Log.d("ContactActivity", "User: $user")
                // Update the adapter with the user data as a single-item list
                contactAdapter.setUser(listOf(user))
                contactAdapter.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(
                    this@ContactActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showLoading() {
        binding.contactRecyclerview.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.contactRecyclerview.visibility = View.VISIBLE
    }

    private fun displayDueRoom(data: List<User>) {
        contactAdapter.setUser(data)
        contactAdapter.notifyDataSetChanged()
    }

    private fun showErrorContent() {
        Toast.makeText(
            this,
            "An error occurred. Please try again.",
            Toast.LENGTH_SHORT
        ).show()
    }
}
