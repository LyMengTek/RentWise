package kh.edu.rupp.ite.rentwise.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kh.edu.rupp.ite.rentwise.R
import kh.edu.rupp.ite.rentwise.adapter.BillingAdapter
import kh.edu.rupp.ite.rentwise.adapter.ContactAdapter
import kh.edu.rupp.ite.rentwise.api.RetrofitClient
import kh.edu.rupp.ite.rentwise.databinding.ActivityContactBinding
import kh.edu.rupp.ite.rentwise.model.User
import retrofit2.Call
import retrofit2.Response

class ContactActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactBinding;
    private lateinit var contactAdapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        showContact()
    }

    private fun setupRecyclerView() {
        contactAdapter = ContactAdapter(listOf()) // Start with an empty list
        binding.contactRecyclerview.layoutManager = LinearLayoutManager(this)
        binding.contactRecyclerview.adapter = contactAdapter
    }

    private fun showContact(){
        RetrofitClient.instance.getUser().enqueue(object : retrofit2.Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful){
                    val user = response.body()
                    Log.d("ContactActivity", "User: $user")
                    user?.let {
                        // Handle the user object here
                        contactAdapter.setUser(listOf(it))
                    }
                } else {
                    Toast.makeText(
                        this@ContactActivity,
                        "Error while loading data from server",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(
                    this@ContactActivity,
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}