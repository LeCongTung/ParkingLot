package com.letung.parkinglot.feature.addCar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.profile.ProfileActivity
import com.letung.parkinglot.model.UserAccount
import com.letung.parkinglot.model.UserCar
import kotlinx.android.synthetic.main.activity_add_car.*

class AddCarActivity : AppCompatActivity() {
    val carTypeList = arrayOf("Xe 5 chỗ", "xe 7 chỗ", "Xe 9 chỗ", "Xe 16 chỗ")
    var typeChoice: String? = ""
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_car)
        database = FirebaseDatabase.getInstance().getReference("Account/${Account.DATA_NAME}/userCar")
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, carTypeList)
        spinner_carType.adapter = arrayAdapter
        spinner_carType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                typeChoice = carTypeList[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        eventListen()
        backToProfileActivity()
    }

    private fun checkCondition() : Boolean{
        //val emailPattern = "[a-zA-Z0-9._-]+[A - Z][a-z]+\\.+[a-z]+"
        //val carNumber = "[1 - 9]+[1 - 9]+[A - Z]+[1 - 9]+[1 - 9]+[1 - 9]+[1 - 9]+[1 - 9]"
        if(edt_nameCar.text.toString().isEmpty() || edt_identityCar.text.toString().isEmpty()){
            Toast.makeText(this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
            return false
        }else
            return true
    }
    private fun eventListen(){
        btn_continueAddCar.setOnClickListener(){
            if(checkCondition()){
                setUpDataBase()
            }
        }
    }
    private fun setUpDataBase(){
        val userCarName = edt_nameCar.text.toString()
        val userCarNumber = edt_identityCar.text.toString()
        val userCar = UserCar(userCarName, userCarNumber, typeChoice)
        database.child(userCarNumber).setValue(userCar).addOnSuccessListener {
            Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT).show()
            Log.d("Khoa", "Chay")
            finish()
        }.addOnFailureListener{
            Log.d("Khoa", "loi")
        }
    }

    private fun backToProfileActivity(){
        imgbtn_backToMainActivity.setOnClickListener(){
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}