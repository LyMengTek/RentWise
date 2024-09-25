package kh.edu.rupp.ite.rentwise.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import kh.edu.rupp.ite.rentwise.databinding.ActivityRegisterBinding

class RegisterActivity : ComponentActivity() {

    private lateinit var binding: ActivityRegisterBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener on the "Register" button
        binding.btnlogin.setOnClickListener {
            // Navigate to the RegisterPageActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

}