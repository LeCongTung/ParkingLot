package com.letung.parkinglot.feature.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.chooseUserCar.ChooseUserCarActivity
import com.letung.parkinglot.feature.parking.ParkingActivity
import com.letung.parkinglot.feature.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventListener()
    }
    private fun eventListener(){
        btn_moveToProfile.setOnClickListener(){
//            Toast.makeText(this, "chuyển sang ${Account.DATA_NAME}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btn_moveToParking.setOnClickListener(){
//            Toast.makeText(this, "chuyển sang ${Account.DATA_NAME}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ChooseUserCarActivity::class.java))
        }
    }


}