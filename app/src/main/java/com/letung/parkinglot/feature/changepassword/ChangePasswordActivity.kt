package com.letung.parkinglot.feature.changepassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.R
import com.letung.parkinglot.feature.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        accountPhone()
        eventListener()
        backToProfile()
    }

    private fun checkConditonPassword(): Boolean {
        if (edt_currentPassword.text.toString().isEmpty()) {
            Toast.makeText(this, "Chưa nhập mật khẩu hiện tại", Toast.LENGTH_SHORT).show()
            return false
        } else {
            if (edt_newPassword.text.toString().isEmpty()) {
                Toast.makeText(this, "Chưa nhập mật khẩu mới", Toast.LENGTH_SHORT).show()
                return false
            } else {
                if (edt_confirmNewPassword.text.toString().isEmpty()) {
                    Toast.makeText(this, "Chưa xác nhận mật khẩu mới", Toast.LENGTH_SHORT).show()
                    return false
                } else {

                    if (edt_newPassword.text.toString() != edt_confirmNewPassword.text.toString()) {
                        Toast.makeText(this, "Xác nhận mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show()
                        return false
                    } else
                        return true

                }
            }
        }
    }

    private fun accountPhone() {
        val intent = intent
        tv_accountPhone.text = intent.getStringExtra("data")
    }

    private fun eventListener() {
        btn_continueUpdatePassword.setOnClickListener() {
            if (checkConditonPassword()) {
                val userCurrentPassword = edt_currentPassword.text.toString()
                val userPassword = edt_newPassword.text.toString()
                val userPhone = tv_accountPhone.text.toString()
                //updateData(userPhone, userPassword)
                resetDatabase(userPhone, userCurrentPassword,userPassword)
            }
        }
    }
    private fun resetDatabase(userPhone: String, userCurrentPassword : String,userPassword: String) {
        database = FirebaseDatabase.getInstance().getReference("Account")
        database.child(userPhone).get().addOnSuccessListener {
            if (it.exists()) {
                if (it.child("userPassword").value.toString() == userCurrentPassword) {
                    updateData(userPhone, userPassword)
                }else {
                    Toast.makeText(this, "Nhập sai mật khẩu hiện tại", Toast.LENGTH_SHORT).show()
            }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Thất bại", Toast.LENGTH_SHORT).show()
        }
    }
    private fun updateData(userPhone: String, userPassword: String) {
        database = FirebaseDatabase.getInstance().getReference("Account")
        val user = mapOf<String, String>(
            "userPassword" to userPassword
        )
        database.child(userPhone).updateChildren(user).addOnSuccessListener {
            Toast.makeText(this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show()
            edt_currentPassword.text?.clear()
            edt_newPassword.text?.clear()
            edt_confirmNewPassword.text?.clear()
        }.addOnFailureListener {
            Toast.makeText(this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun backToProfile(){
        imgbtn_backToMainActivity.setOnClickListener(){
            onBackPressed()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}