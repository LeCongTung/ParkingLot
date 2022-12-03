package com.letung.parkinglot.feature.main

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.chooseUserCar.ChooseUserCarActivity
import com.letung.parkinglot.feature.parking.ParkingActivity
import com.letung.parkinglot.feature.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_newfeature_notice.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        eventListener()
    }
    private fun eventListener(){
        img_userProfile.setOnClickListener(){
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        car_parking.setOnClickListener(){
            startActivity(Intent(this, ChooseUserCarActivity::class.java))
        }
        car_rent.setOnClickListener(){
            setDialogNewFeatureNotice()
        }
        car_washing.setOnClickListener(){
            setDialogNewFeatureNotice()
        }
    }

    private fun setDialogNewFeatureNotice(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_newfeature_notice)
        if(dialog.window != null){
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.setCancelable(false)
        dialog.show()
        dialog.btn_continueDismiss.setOnClickListener(){
            dialog.dismiss()
        }
    }

}