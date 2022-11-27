package com.letung.parkinglot.feature.spashScreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.main.MainActivity
import com.letung.parkinglot.feature.signIn.SignInActivity
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpashScreenActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)
        database = FirebaseDatabase.getInstance().getReference("Account")
        fetchAccount()
    }

    //    Delay and move to another Activity
    private fun moveToActivity() {
//    Cách 1
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            Account.CODE_ISUSER = true
            startActivity(Intent(this@SpashScreenActivity, MainActivity::class.java))
            finish()
        }
//    Cách 2
//        Handler().postDelayed({
//            startActivity(Intent(this@SpashScreenActivity, MainActivity::class.java))
//        }, 3000)
    }
    private fun moveToSignIn(){
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            startActivity(Intent(this@SpashScreenActivity, SignInActivity::class.java))
            finish()
        }
    }

    private fun fetchAccount() {
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val defaultValuePhone = sharedPreference.getString(Account.CODE_DATA_PHONENUMBER,"").toString()
        val defaultValuePass = sharedPreference.getString(Account.CODE_DATA_PASSWORD,"").toString()
        Log.e("tungdeptrai", "${defaultValuePhone} ${defaultValuePass}")
        database.child(defaultValuePhone).get().addOnSuccessListener {
            if (it.exists()) {
                if (it.child("userPassword").value.toString() == defaultValuePass) {
                    Log.e("tungdeptrai", "maiyeu22")
                    Account.DATA_NAME = defaultValuePhone
                    moveToActivity()
                }else{
                    moveToSignIn()
                }
            }
        }
    }
}