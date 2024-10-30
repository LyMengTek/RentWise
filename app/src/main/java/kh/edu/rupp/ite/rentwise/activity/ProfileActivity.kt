package kh.edu.rupp.ite.rentwise.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import kh.edu.rupp.ite.rentwise.R
import kh.edu.rupp.ite.rentwise.adapter.ContactAdapter
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.databinding.ActivityContactBinding
import kh.edu.rupp.ite.rentwise.databinding.ActivityProfileBinding
import kh.edu.rupp.ite.rentwise.model.ApiState
import kh.edu.rupp.ite.rentwise.model.Invoice
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.viewmodel.ContactViewModel
import kh.edu.rupp.ite.rentwise.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private val viewModel = ProfileViewModel()

    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.profileState.observe(this) { profile ->
            when (profile.state) {

                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    // Wrap data in a list if dueRoomState.data is not null
                    displayProfile(profile)
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
            }
        }
        viewModel.loadProfile()

        binding.backAccount.setOnClickListener {
            val intent = Intent(this, LandlordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayProfile(profile: ApiState<User>) {
        profile.data?.let { user ->
            binding.profileUsername.text = user.username
            binding.profileEmail.text = user.email
            Picasso.get()
                .load(user.profilePicture)
                .into(binding.profileImage);
        } ?: run {
            Toast.makeText(this, "Failed to load profile", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showLoading() {

        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE

    }

    private fun showErrorContent() {
        Toast.makeText(
            this,
            "An error occurred. Please try again.",
            Toast.LENGTH_SHORT
        ).show()
    }

}

