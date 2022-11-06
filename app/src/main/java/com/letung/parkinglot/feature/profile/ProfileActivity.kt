package com.letung.parkinglot.feature.profile

import android.content.Intent
import com.letung.parkinglot.extension.Account
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.letung.parkinglot.R
import com.letung.parkinglot.feature.addCar.AddCarActivity
import com.letung.parkinglot.feature.changepassword.ChangePasswordActivity
import com.letung.parkinglot.feature.main.MainActivity
import com.letung.parkinglot.feature.updateprofile.UpdateProfileActivity
import com.letung.parkinglot.feature.userListCar.UserListCarActivity
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setNameForAccount()
        moveToUpdateProfileActivity()
        moveToChangePasswordActivity()
        moveToAddCarActivity()
        moveToListCar()
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
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show()
            //truyền dữ liệu đi
            val intent = Intent(this, ChangePasswordActivity::class.java)
            intent.putExtra("data", tv_accountPhone.text.toString())
            startActivity(intent)
        }
    }

    private fun moveToAddCarActivity() {
        edt_addCar.setOnClickListener() {
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show()
            //truyền dữ liệu đi
            val intent = Intent(this, AddCarActivity::class.java)
            intent.putExtra("data", tv_accountPhone.text.toString())
            startActivity(intent)
        }
    }

    private fun moveToListCar() {
        edt_listCar.setOnClickListener() {
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show()
            //truyền dữ liệu đi
            startActivity(Intent(this, UserListCarActivity::class.java))
        }

    }

    private fun backToMainActivity() {
        imgbtn_backToMainActivity.setOnClickListener() {
            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show()

            //startActivity(Intent(this, MainActivity::class.java))
        }
    }

}