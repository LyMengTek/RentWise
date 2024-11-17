package kh.edu.rupp.ite.rentwise.activity.other

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kh.edu.rupp.ite.rentwise.R
import kh.edu.rupp.ite.rentwise.activity.login_register.LandlordActivity
import kh.edu.rupp.ite.rentwise.databinding.ActivityCalculatingBillBinding
import kh.edu.rupp.ite.rentwise.fragment.BillingFragment
import kh.edu.rupp.ite.rentwise.fragment.UpcomingFragment

class CalculatorBillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalculatingBillBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityCalculatingBillBinding.inflate(layoutInflater)
        setContentView(binding.root) // Set the content view to the binding's root

        // Set up the initial fragment
        if (savedInstanceState == null) {
            replaceFragment(BillingFragment()) // Load the default fragmentt
        }

        // Set up BottomNavigationView listener
        binding.calculatorBillNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mnu_billing -> {
                    replaceFragment(BillingFragment())
                    true
                }
                R.id.mnu_upcoming -> {
                    replaceFragment(UpcomingFragment())
                    true
                }
                else -> false
            }
        }

        binding.backToHome.setOnClickListener {
            val intent = Intent(this, LandlordActivity::class.java)
            startActivity(intent)
        }
    }

    // Helper function to replace the displayed fragment
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.calculatorBillFragment, fragment)
            .commit()
    }
}