package com.letung.parkinglot.feature.signIn

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.letung.parkinglot.R
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.room.Database
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.extension.App
import com.letung.parkinglot.feature.customer.passer.fill_information.fillInformation
import com.letung.parkinglot.feature.main.MainActivity
import com.letung.parkinglot.feature.signUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_fill_information.*
import kotlinx.android.synthetic.main.activity_fill_information.edt_name
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_in.edt_password
import kotlinx.android.synthetic.main.activity_sign_in.edt_phone
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    //private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("Account")
        eventMoveToSignUp()
        eventListener()
    }

    private fun eventMoveToSignUp(){
        moveToSignUp.setOnClickListener(){
            auth.signOut()
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
        btn_passerRegisterParkingLot.setOnClickListener(){
            startActivity(Intent(this, fillInformation::class.java))
        }
    }

    private fun checkConditionPhone() : Boolean{
        if(edt_phone.text.toString().isEmpty()){
            Toast.makeText(this, "Y??u c???u nh???p ?????y ????? S??T", Toast.LENGTH_SHORT).show()
            edt_phone.setText("")
            return false
        }else
            if(edt_phone.text.toString().length ==9 || edt_phone.text.toString().length == 10){
                return true
            }else{
                Toast.makeText(this, "Y??u c???u nh???p ????ng S??T", Toast.LENGTH_SHORT).show()
                edt_phone.setText("")
                return false
            }

    }
    private fun checkConditionPassword() : Boolean{
        if(edt_password.text.toString().isEmpty()){
            Toast.makeText(this, "Y??u c???u nh???p ?????y ????? m???t kh???u", Toast.LENGTH_SHORT).show()
            edt_password.setText("")
            return false
        }else
            return true
    }
    private fun eventListener(){
        btn_signIn.setOnClickListener(){
            if(checkConditionPhone() && checkConditionPassword()){
                readData(edt_phone.text.toString(), edt_password.text.toString().trim())
            }
        }
    }
    private fun readData(userAccount : String, pass: String){

        database.child(userAccount).get().addOnSuccessListener {
            if(it.exists()){
                //edt_password.text = it.child("userPassword").value.toString()
                if(it.child("userPassword").value.toString() == pass){
                    Account.DATA_NAME = edt_phone.text.toString()
                    saveKey(userAccount, pass)
                    //var Account.CODE_DATA_NAME :String = edt_phone.text.toString()
                    setLoadingDialog()
                    setSplashScreen()
                }else
                    Toast.makeText(this, "Sai m???t kh???u", Toast.LENGTH_SHORT).show()
            }else
            {
                Toast.makeText(this, "T??i kho???n kh??ng t???n t???i", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(){
            Toast.makeText(this, "T??m t??i kho???n th???t b???i", Toast.LENGTH_SHORT).show()
        }
    }
    private fun saveKey(phone: String, pass: String){
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString(Account.CODE_DATA_PHONENUMBER,phone)
        editor.putString(Account.CODE_DATA_PASSWORD , pass)
        editor.commit()
    }

    private fun setLoadingDialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_loading)
        if(dialog.window != null){
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun setSplashScreen(){
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            startActivity(Intent(this@SignInActivity, MainActivity::class.java))
            Toast.makeText(this@SignInActivity, "????ng nh???p th??nh c??ng", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}