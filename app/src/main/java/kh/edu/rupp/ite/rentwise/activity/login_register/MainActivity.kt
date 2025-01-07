package kh.edu.rupp.ite.rentwise.activity.login_register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.databinding.ActivityLoginBinding
import kh.edu.rupp.ite.rentwise.model.LoginRequest
import kh.edu.rupp.ite.rentwise.model.LoginResponse
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
                    if (loginResponse != null) {
                        handleSuccessfulLogin(loginResponse)
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Unexpected response format",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val errorMessage = response.message() ?: "Login Failed! Check your credentials."
                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun handleSuccessfulLogin(loginResponse: LoginResponse) {
        // Save token to SharedPreferences
        saveTokenToPreferences(loginResponse.user.token, loginResponse.user.id.toString(), loginResponse.user.username)

        // Show success message
        Toast.makeText(
            this,
            "Login Successful! Welcome ${loginResponse.user.username}",
            Toast.LENGTH_SHORT
        ).show()

        // Navigate based on user type
        when (loginResponse.user.user_type) {
            "landlord" -> navigateToActivity(LandlordActivity::class.java)
//            "renter" -> navigateToActivity(RenterActivity::class.java)
            else -> Toast.makeText(
                this,
                "Unknown user type: ${loginResponse.user.user_type}",
                Toast.LENGTH_SHORT
            ).show()
        }

        finish()
    }

    private fun saveTokenToPreferences(token: String, userId: String, name: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("token", token)
        editor.putString("userId", userId)  // Save userId
        editor.putString("name", name)      // Save name
        editor.apply()
        Log.d("SharedPreferences", "Token saved: $token")
        Log.d("SharedPreferences", "UserId saved: $userId")
        Log.d("SharedPreferences", "Name saved: $name")
    }



    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}