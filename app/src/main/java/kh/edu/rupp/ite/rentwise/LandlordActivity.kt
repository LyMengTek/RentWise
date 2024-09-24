package kh.edu.rupp.ite.rentwise

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import kh.edu.rupp.ite.rentwise.databinding.ActivityLandlordInterfaceBinding
import kh.edu.rupp.ite.rentwise.databinding.ActivityLoginBinding
import kh.edu.rupp.ite.rentwise.databinding.ActivityRegisterBinding

class LandlordActivity : ComponentActivity() {

    private lateinit var binding: ActivityLandlordInterfaceBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLandlordInterfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

}