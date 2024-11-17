package kh.edu.rupp.ite.rentwise.activity.login_register

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import kh.edu.rupp.ite.rentwise.activity.other.CalculatorBillActivity
import kh.edu.rupp.ite.rentwise.activity.other.ContactActivity
import kh.edu.rupp.ite.rentwise.activity.landloard_setup.ShowRoomSetupOptionsActivity
import kh.edu.rupp.ite.rentwise.databinding.ActivityLandlordInterfaceBinding

class LandlordActivity : ComponentActivity() {

    private lateinit var binding: ActivityLandlordInterfaceBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandlordInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calBillHomeBtn.setOnClickListener {
            val intent = Intent(this, CalculatorBillActivity::class.java)
            startActivity(intent)
        }

        binding.contactHomeBtn.setOnClickListener{
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        }

        binding.calBillHomeBtn.setOnClickListener {
            val intent = Intent(this, CalculatorBillActivity::class.java)
            startActivity(intent)
        }

        binding.SetupRoomBtn.setOnClickListener {
            val intent = Intent(this, ShowRoomSetupOptionsActivity::class.java)
            startActivity(intent)
        }

    }

}