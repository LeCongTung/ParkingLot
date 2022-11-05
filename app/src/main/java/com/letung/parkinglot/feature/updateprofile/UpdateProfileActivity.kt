package com.letung.parkinglot.feature.updateprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_update_profile.*

class UpdateProfileActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)
        updateProfile()
        setEventForPhone()
        eventListener()
        backToProfileActivity()
    }

    private fun updateProfile(){
        val intent = intent
        edt_phone2.setText(intent.getStringExtra("data"))
        //edt_phone2.setText("0935196475")

    }
    private fun setEventForPhone(){
        val userPhone : String = edt_phone2.text.toString()
        readData(userPhone)
        edt_phone2.setOnClickListener(){
            Toast.makeText(this, "Không thể chỉnh sửa SĐT đã tạo", Toast.LENGTH_SHORT).show()

        }
    }

    private fun readData(userPhone: String) {
        database = FirebaseDatabase.getInstance().getReference("Account")
        database.child(userPhone).get().addOnSuccessListener {
            if(it.exists()){
                val userName = it.child("userName").value.toString()
                val userID = it.child("userIdentity").value.toString()
                edt_name.setText("${userName}")
                edt_identity2.setText("${userID}")

            }else{
                Toast.makeText(this, "Không tồn tại", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(this, "Truy vấn thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkConditionName() :Boolean{
        if(edt_name.text.toString().isEmpty()){
            Toast.makeText(this, "Yêu cầu nhập tên", Toast.LENGTH_SHORT).show()
            return false
        }else
            return true
    }
    private fun checkConditionIdentity() : Boolean{
        if(edt_identity2.text.toString().isEmpty()){
            Toast.makeText(this, "Yêu cầu nhập đầy đủ CCCD/ CMND", Toast.LENGTH_SHORT).show()
            return false
        }else{
            if(edt_identity2.text.toString().length == 9 || edt_identity2.text.toString().length == 12){
                return true
            }else{
                Toast.makeText(this, "Yêu cầu nhập đúng CCCD/ CMND", Toast.LENGTH_SHORT).show()
                edt_identity2.setText("")
                return false
            }
        }
    }
    private fun eventListener(){
        btn_continueUpdate.setOnClickListener(){
            if(checkConditionName() && checkConditionIdentity()){
                val userPhone = edt_phone2.text.toString()
                val userName = edt_name.text.toString()
                val userID = edt_identity2.text.toString()
                updateData(userPhone, userName, userID)
            }
        }
    }
    private fun updateData(userPhone : String, userName : String, userID : String){
        database = FirebaseDatabase.getInstance().getReference("Account")
        val user = mapOf<String, String>(
            "userName" to userName,
            "userIdentity" to userID
        )
        database.child(userPhone).updateChildren(user).addOnSuccessListener {
            Toast.makeText(this, "Chỉnh sửa thông tin thành công", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Chỉnh sửa thât bại", Toast.LENGTH_SHORT).show()
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