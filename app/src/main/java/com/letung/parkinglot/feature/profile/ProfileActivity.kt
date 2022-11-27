package com.letung.parkinglot.feature.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.addCar.AddCarActivity
import com.letung.parkinglot.feature.changepassword.ChangePasswordActivity
import com.letung.parkinglot.feature.depositUserAccount.depositUserAccountActivity
import com.letung.parkinglot.feature.signIn.SignInActivity
import com.letung.parkinglot.feature.updateprofile.UpdateProfileActivity
import com.letung.parkinglot.feature.userListCar.UserListCarActivity
import com.letung.parkinglot.feature.userListParkingLot.UserListParkingActivity
import kotlinx.android.synthetic.main.activity_profile.*


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
        moveToUserParkingLotList()
        backToMainActivity()
    }

    override fun onRestart() {
        super.onRestart()
        setUserMoney(Account.DATA_NAME)
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
            val sharedPreference =  getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
            sharedPreference.edit().remove(Account.CODE_DATA_PHONENUMBER).commit()
            sharedPreference.edit().remove(Account.CODE_DATA_PASSWORD).commit()
            Account.CODE_ISUSER = false
            finishAffinity()
        }
    }

    private fun moveToUserParkingLotList(){
        edt_History.setOnClickListener(){
            startActivity(Intent(this, UserListParkingActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}