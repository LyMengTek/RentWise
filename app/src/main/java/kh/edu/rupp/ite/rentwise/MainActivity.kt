package kh.edu.rupp.ite.rentwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import kh.edu.rupp.ite.rentwise.databinding.ActivityLoginBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener on the "Register" button
        binding.register.setOnClickListener {
            // Navigate to the RegisterPageActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}

