package kh.edu.rupp.ite.rentwise.activity.other

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.activity.login_register.LandlordActivity
import kh.edu.rupp.ite.rentwise.adapter.Contact.ContactAdapter
import kh.edu.rupp.ite.rentwise.databinding.ActivityContactBinding
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.viewmodel.ContactViewModel

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding
    private lateinit var contactAdapter: ContactAdapter

    // Initialize ViewModel using viewModels delegate
    private val viewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()

        // Fetch contacts when the activity starts
        viewModel.fetchContacts() // No need to pass landlordId

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

    private fun observeViewModel() {
        // Observe the LiveData from the ViewModel
        viewModel.dueContactState.observe(this) { apiState ->
            when (apiState.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    apiState.data?.let { users ->
                        displayDueRoom(users) // Pass the List<User> to the adapter
                    }
                }
                State.error -> {
                    hideLoading()
                    showErrorContent(apiState.errorMessage)
                }
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
        contactAdapter.setUser(data) // Pass the List<User> to the adapter
        contactAdapter.notifyDataSetChanged()
    }

    private fun showErrorContent(message: String?) {
        Toast.makeText(
            this,
            message ?: "An error occurred. Please try again.",
            Toast.LENGTH_SHORT
        ).show()
    }
}