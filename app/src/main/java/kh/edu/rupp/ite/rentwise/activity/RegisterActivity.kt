package kh.edu.rupp.ite.rentwise.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import kh.edu.rupp.ite.rentwise.R
import kh.edu.rupp.ite.rentwise.databinding.ActivityRegisterBinding
import kh.edu.rupp.ite.rentwise.model.State
import kh.edu.rupp.ite.rentwise.viewmodel.RegisterViewModel


class RegisterActivity : ComponentActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()


    private var isProfilePictureClicked = false // To track the button clicked

    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bitmap = result.data?.extras?.get("data") as? Bitmap
                if (bitmap != null) {
                    if (isProfilePictureClicked) {
                        binding.risProfilePicture.setImageBitmap(bitmap)
                    } else {
                        binding.risID.setImageBitmap(bitmap)
                    }
                } else {
                    Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Camera operation cancelled", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRegisterButton()
        setupCameraButtons()
        observeRegisterState()
        setupUserTypeSelector()

    }

    private var selectedUserType: String = "renter" // Default selection

    private fun setupUserTypeSelector() {
        val rentersButton = binding.btnRenters
        val landlordsButton = binding.btnLandlords

        // Default styles for buttons
        val unselectedBackground = ContextCompat.getDrawable(this, R.color.unselected_background)
        val selectedBackground = ContextCompat.getDrawable(this, R.color.selected_background)
        val unselectedTextColor = ContextCompat.getColor(this, R.color.unselected_text_color)
        val selectedTextColor = ContextCompat.getColor(this, R.color.selected_text_color)

        // Set initial selection
        rentersButton.background = selectedBackground
        rentersButton.setTextColor(selectedTextColor)

        landlordsButton.background = unselectedBackground
        landlordsButton.setTextColor(unselectedTextColor)

        // Add click listeners
        rentersButton.setOnClickListener {
            selectedUserType = "renter"

            // Update styles
            rentersButton.background = selectedBackground
            rentersButton.setTextColor(selectedTextColor)

            landlordsButton.background = unselectedBackground
            landlordsButton.setTextColor(unselectedTextColor)
        }

        landlordsButton.setOnClickListener {
            selectedUserType = "landlord"

            // Update styles
            landlordsButton.background = selectedBackground
            landlordsButton.setTextColor(selectedTextColor)

            rentersButton.background = unselectedBackground
            rentersButton.setTextColor(unselectedTextColor)
        }
    }


    private fun setupRegisterButton() {
        binding.btnregister.setOnClickListener {
            val username =
                binding.risFirstname.text.toString() + " " + binding.risLastname.text.toString()
            val email = binding.risEmail.text.toString()
            val password = binding.risPassword.text.toString()
            val confirmPassword = binding.risConPassword.text.toString()
            val phoneNumber = binding.risPhonenumber.text.toString()
            val user_type = selectedUserType

            // Input validation
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Trigger ViewModel to handle registration
            registerViewModel.registerUser(username, email, password, phoneNumber, user_type)
            Log.d("RegisterPayload", "Payload: username=$username, email=$email, phone=$phoneNumber, user_type=$selectedUserType")

        }
    }

    private fun setupCameraButtons() {
        binding.risProfilePicture.setOnClickListener {
            isProfilePictureClicked = true
            checkAndRequestCameraPermission()
        }

        binding.risID.setOnClickListener {
            isProfilePictureClicked = false
            checkAndRequestCameraPermission()
        }
    }

    private fun checkAndRequestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            cameraLauncher.launch(cameraIntent)
        } else {
            Toast.makeText(this, "No Camera app found to handle the intent", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun observeRegisterState() {
        registerViewModel.registerState.observe(this, Observer { apiState ->
            when (apiState.state) {
                State.loading -> {
                    Toast.makeText(this, "Registering, please wait...", Toast.LENGTH_SHORT).show()
                }

                State.success -> {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show()
                    // Navigate to another screen or clear form
                }

                State.error -> {
                    Toast.makeText(
                        this,
                        "Registration failed. Please try again.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }
}
