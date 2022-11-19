package com.letung.parkinglot.feature.profile

import android.content.Intent
import com.letung.parkinglot.extension.Account
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.R
import com.letung.parkinglot.feature.addCar.AddCarActivity
import com.letung.parkinglot.feature.changepassword.ChangePasswordActivity
import com.letung.parkinglot.feature.depositUserAccount.depositUserAccountActivity
import com.letung.parkinglot.feature.main.MainActivity
import com.letung.parkinglot.feature.signIn.SignInActivity
import com.letung.parkinglot.feature.updateprofile.UpdateProfileActivity
import com.letung.parkinglot.feature.userListCar.UserListCarActivity
import kotlinx.android.synthetic.main.activity_profile.*
import kotlin.system.exitProcess

class ProfileActivity : AppCompatActivity() {
    private lateinit var databaseMoney : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        databaseMoney = FirebaseDatabase.getInstance().getReference("Account")
        setNameForAccount()
        setUserMoney(Account.DATA_NAME)
        moveToUpdateProfileActivity()
        moveToChangePasswordActivity()
        moveToAddCarActivity()
        moveToListCar()
        moveToSignInActivity()
        moveToDepositUserAccount()
        backToMainActivity()
    }

    private fun setUserMoney(dataName: String) {
        databaseMoney.child(dataName).get().addOnSuccessListener { 
            if(it.exists()){
                val userMoney = it.child("userMoney").value.toString()
                tv_accountMoney2.text = userMoney
            }else{
                Toast.makeText(this, "Không tồn tại", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(){
            Toast.makeText(this, "Truy vấn thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setNameForAccount() {
        tv_accountPhone.text = Account.DATA_NAME
        //tv_accountPhone.text = "0896203075"
    }

    private fun moveToUpdateProfileActivity() {
        edt_editProfile.setOnClickListener() {
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show()
            //truyền dữ liệu đi
            val intent = Intent(this, UpdateProfileActivity::class.java)
            intent.putExtra("data", tv_accountPhone.text.toString())
            startActivity(intent)
        }
    }

    private fun moveToChangePasswordActivity() {
        edt_changePassword.setOnClickListener() {
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("data", tv_accountPhone.text.toString())
            startActivity(intent)
        }
    }

    private fun moveToAddCarActivity() {
        edt_addCar.setOnClickListener() {
            val intent = Intent(this, AddCarActivity::class.java)
            intent.putExtra("data", tv_accountPhone.text.toString())
            startActivity(intent)
        }
    }

    private fun moveToListCar() {
        edt_listCar.setOnClickListener() {
            startActivity(Intent(this, UserListCarActivity::class.java))
        }

    }

    private fun backToMainActivity() {
        imgbtn_backToMainActivity.setOnClickListener() {
            onBackPressed()
        }
    }

    private fun moveToDepositUserAccount(){
        edt_depositMoney.setOnClickListener(){
            startActivity(Intent(this, depositUserAccountActivity::class.java))
        }
    }

    private fun moveToSignInActivity(){
        edt_signOut.setOnClickListener(){
            startActivity(Intent(this, SignInActivity::class.java))
            //=> crack app
//            moveTaskToBack(true);
//            exitProcess(-1)
            finishAffinity()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}