package kh.edu.rupp.ite.rentwise.activity.landloard_setup


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import kh.edu.rupp.ite.rentwise.activity.login_register.LandlordActivity
import kh.edu.rupp.ite.rentwise.databinding.ActivityShowRoomSetupOptionsBinding

class ShowRoomSetupOptionsActivity : ComponentActivity() {

    private lateinit var binding:ActivityShowRoomSetupOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowRoomSetupOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backToHome.setOnClickListener {
            val intent = Intent(this, LandlordActivity::class.java)
            startActivity(intent)
        }

        binding.setupRoomTypeAndPricing.setOnClickListener {
            val intent = Intent( this, SetupRoomTypeAndPricingActivity::class.java)
            startActivity(intent)
        }

        binding.assignRoomToRoomType.setOnClickListener {
            val intent = Intent(this, AssignRoomActivity::class.java)
            startActivity(intent)
        }

    }
}