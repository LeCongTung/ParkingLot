package com.letung.parkinglot.feature.userListParkingLot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.letung.parkinglot.R
import kotlinx.android.synthetic.main.activity_user_list_parking.*

class UserListParkingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list_parking)

        backToMainActivity()
    }

    private fun backToMainActivity(){
        imgbtn_backToMainActivity.setOnClickListener(){
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}