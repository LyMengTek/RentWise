package kh.edu.rupp.ite.rentwise.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kh.edu.rupp.ite.rentwise.activity.login_register.LandlordActivity
import kh.edu.rupp.ite.rentwise.activity.login_register.MainActivity
import kh.edu.rupp.ite.rentwise.databinding.ActivityProfileBinding
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.model.User
import kh.edu.rupp.ite.rentwise.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backAccount.setOnClickListener {
            val intent = Intent(this, LandlordActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Initialize the ViewModel
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        viewModel = ProfileViewModel(sharedPreferences)

        // Get the userId from intent or SharedPreferences
        val userId = intent.getStringExtra("USER_ID") ?: getLoggedInUserId()

        // Log the userId for debugging
        Log.d("ProfileActivity", "UserId: $userId")

        if (userId.isNullOrEmpty()) {
            Toast.makeText(this, "User ID not found. Redirecting to login...", Toast.LENGTH_SHORT).show()
            redirectToLogin()
            return
        }

        // Load the user profile
        viewModel.loadProfile(userId)

        // Observe the profile data
        viewModel.profileState.observe(this) { profile ->
            when (profile.state) {
                State.loading -> showLoading()
                State.success -> {
                    hideLoading()
                    displayProfile(profile.data)
                }
                State.error -> {
                    hideLoading()
                    showErrorContent()
                }
            }
        }
    }

    private fun displayProfile(user: User?) {
        user?.let {
            binding.profileUsername.text = it.username
            binding.profileEmail.text = it.email
            binding.userPhonenumber.text = "Phone Number : " + it.phone_number
            Picasso.get().load(it.profile_picture).into(binding.pictureCard)
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorContent() {
        Toast.makeText(this, "Error loading profile", Toast.LENGTH_SHORT).show()
    }

    private fun redirectToLogin() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Helper function to get logged-in user ID
    private fun getLoggedInUserId(): String? {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        return sharedPreferences.getString("userId", null)
    }
}
