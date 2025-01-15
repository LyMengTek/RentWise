package kh.edu.rupp.ite.rentwise.activity.other

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.adapter.Contact.ContactAdapter
import kh.edu.rupp.ite.rentwise.databinding.ActivityContactBinding
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.Contact
import kh.edu.rupp.ite.rentwise.viewmodel.ContactViewModel

class ContactViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding
    private lateinit var contactAdapter: ContactAdapter

    private val viewModel: ContactViewModel by viewModels {
        ContactViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        viewModel.fetchContacts()

        binding.backToHome.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter(emptyList())
        binding.contactRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@ContactActivity)
            adapter = contactAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        viewModel.dueContactState.observe(this) { apiState ->
            when (apiState.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    apiState.data?.let { contacts ->
                        if (contacts.isEmpty()) {
                            showEmptyState()
                        } else {
                            displayContacts(contacts)
                        }
                    } ?: showErrorContent("No data available")
                }
                State.error -> {
                    hideLoading()
                    showErrorContent(apiState.errorMessage)
                }
            }
        }
    }

    private fun showLoading() {
        binding.apply {
            contactRecyclerview.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        binding.apply {
            progressBar.visibility = View.GONE
            contactRecyclerview.visibility = View.VISIBLE
        }
    }

    private fun showEmptyState() {
        binding.apply {
            contactRecyclerview.visibility = View.GONE
            error.visibility = View.VISIBLE
        }
        showErrorContent("No contacts available")
    }

    private fun displayContacts(data: List<Contact>) {
        try {
            binding.error.visibility = View.GONE
            binding.contactRecyclerview.visibility = View.VISIBLE
            contactAdapter.setContacts(data)
        } catch (e: Exception) {
            Log.e("ContactActivity", "Error updating adapter", e)
            showErrorContent("Error displaying contacts")
        }
    }

    private fun showErrorContent(message: String?) {
        try {
            Toast.makeText(
                this,
                message ?: "An error occurred. Please try again.",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Log.e("ContactActivity", "Error showing toast", e)
        }
    }
}