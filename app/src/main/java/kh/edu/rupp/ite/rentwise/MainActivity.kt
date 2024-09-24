package kh.edu.rupp.ite.rentwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import kh.edu.rupp.ite.rentwise.databinding.ActivityLoginBinding
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle login button click
        binding.btnlogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle register button click
        binding.btnregister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        RetrofitClient.instance.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Toast.makeText(this@MainActivity, "Login Success! Token: ${loginResponse?.token}", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@MainActivity, LandlordActivity::class.java)
                    intent.putExtra("TOKEN", loginResponse?.token) // Pass the token if needed
                    startActivity(intent)

                    finish()
                    // Handle successful login, e.g., save token and navigate to the next screen
                } else {
                    Toast.makeText(this@MainActivity, "Login Failed! Check your credentials.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


