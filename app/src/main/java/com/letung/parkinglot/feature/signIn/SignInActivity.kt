package com.letung.parkinglot.feature.signIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.letung.parkinglot.R
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import com.letung.parkinglot.feature.signUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()
        eventMoveToSignUp()
    }
    private fun eventMoveToSignUp(){
        moveToSignUp.setOnClickListener(){
            auth.signOut()
            startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
        }
    }
}