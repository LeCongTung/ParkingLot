package com.letung.parkinglot.feature.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.letung.parkinglot.R
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.edt_name
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.feature.OTP_remake.OTP_RemakeActivity
import com.letung.parkinglot.feature.customer.passer.fill_information.fillInformation
import com.letung.parkinglot.feature.signIn.SignInActivity
import com.letung.parkinglot.model.User
import com.letung.parkinglot.model.UserAccount
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var sendOTPBtn: Button
    private lateinit var phoneNumberET: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var number: String

    //private lateinit var mProgressBar : ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        database = FirebaseDatabase.getInstance().getReference("Account")
        init()
        eventListener()
        moveToSignIn()
        moveToPasserRegisterParkinglot()
    }

    private fun checkCondition(): Boolean {
        if (edt_name.text.toString().isEmpty()) {
            Toast.makeText(this, "Yêu cầu nhập đầy đủ họ và tên", Toast.LENGTH_SHORT).show()
            edt_name.setText("")
            return false
        } else
            if (edt_password.text.toString().isEmpty()) {
                Toast.makeText(this, "Yêu cầu nhập mật khẩu", Toast.LENGTH_SHORT).show()
                edt_password.setText("")
                return false
            } else
                if (edt_rewritePassword.text.toString().isEmpty()) {
                    Toast.makeText(this, "Yêu cầu xác nhận mật khẩu", Toast.LENGTH_SHORT).show()
                    edt_rewritePassword.setText("")
                    return false
                } else
                    if (edt_rewritePassword.text.toString() != edt_password.text.toString()) {
                        Toast.makeText(this, "Không trùng khớp mật khẩu", Toast.LENGTH_SHORT).show()
                        return false
                    } else
                        return true
    }

    private fun checkConditionIdentity(): Boolean {
        if (edt_identity2.text.toString().isEmpty()) {
            Toast.makeText(this, "Yêu cầu nhập đầy đủ CCCD/ CMND", Toast.LENGTH_SHORT).show()
            //edt_identity2.setText("")
            return false
        } else
            if (edt_identity2.text.toString().length == 9 || edt_identity2.text.toString().length == 12) {
                return true
            } else{
                Toast.makeText(this, "Yêu cầu nhập đúng CCCD/ CMND", Toast.LENGTH_SHORT).show()
                edt_identity2.setText("")
                return false

            }
    }

    private fun checkConditionNumberPhone(): Boolean {
        if (phoneEditTextNumber.text.toString().isEmpty()) {
            Toast.makeText(this, "Yêu cầu nhập đầy đủ SDT", Toast.LENGTH_SHORT).show()
            phoneEditTextNumber.setText("")
            return false
        } else
            if (phoneEditTextNumber.text.toString().length == 9 || phoneEditTextNumber.text.toString().length == 10) {
                return true
            } else{
                Toast.makeText(this, "Yêu cầu nhập đúng SDT", Toast.LENGTH_SHORT).show()
                phoneEditTextNumber.setText("")
                return false
            }
    }

    private fun eventListener() {
        sendOTPBtn.setOnClickListener() {
            if (checkCondition() && checkConditionIdentity() && checkConditionNumberPhone()) {
                number = phoneNumberET.text.trim().toString()
                number = "+84$number"
                //mProgressBar.visibility = View.VISIBLE
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber(number)       // Phone number to verify
                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                    .setActivity(this)                 // Activity (for callback binding)
                    .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
                setUpDatabase()
            }
        }
    }

    private fun setUpDatabase(){
        val userName = edt_name.text.toString()
        val userPhone = phoneEditTextNumber.text.toString()
        val userIdentity = edt_identity2.text.toString()
        val userPassword = edt_password.text.toString()

        val userAccount = UserAccount(userName, userPhone, userIdentity, userPassword)
        database.child(userPhone).setValue(userAccount).addOnSuccessListener {
            Log.d("Khoa", "Chay")
//            edt_name.text?.clear()
//            phoneEditTextNumber.text?.clear()
//            edt_identity2.text?.clear()
//            edt_password.text?.clear()
//            edt_rewritePassword.text?.clear()
        }.addOnFailureListener{
            Log.d("Khoa", "loi")
        }
    }

    private fun moveToSignIn(){
        moveToSignIn.setOnClickListener(){
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    private fun moveToPasserRegisterParkinglot(){
        btn_registerParkingLot.setOnClickListener(){
            startActivity(Intent(this, fillInformation::class.java))
        }
    }

    private fun init() {
        //mProgressBar = findViewById(R.id.phoneProgressBar)
        //mProgressBar.visibility = View.INVISIBLE
        sendOTPBtn = findViewById(R.id.sendOTPBtn)
        phoneNumberET = findViewById(R.id.phoneEditTextNumber)
        auth = FirebaseAuth.getInstance()
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this, "Xác thực thành công", Toast.LENGTH_SHORT).show()
                    sendToMain()
                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("TAG", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
                //mProgressBar.visibility = View.INVISIBLE
            }
    }

    private fun sendToMain() {
        startActivity(Intent(this, SignInActivity::class.java))
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // This callback is invoked in an invalid request for verification is made,
            // for instance if the the phone number format is not valid.

            if (e is FirebaseAuthInvalidCredentialsException) {
                // Invalid request
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            } else if (e is FirebaseTooManyRequestsException) {
                // The SMS quota for the project has been exceeded
                Log.d("TAG", "onVerificationFailed: ${e.toString()}")
            }
            //mProgressBar.visibility = View.VISIBLE
            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            // Save verification ID and resending token so we can use them later
            val intent = Intent(this@SignUpActivity, OTP_RemakeActivity::class.java)
            intent.putExtra("OTP", verificationId)
            intent.putExtra("resendToken", token)
            intent.putExtra("phoneNumber", number)
            startActivity(intent)
            //mProgressBar.visibility = View.INVISIBLE
        }
    }


    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }


}