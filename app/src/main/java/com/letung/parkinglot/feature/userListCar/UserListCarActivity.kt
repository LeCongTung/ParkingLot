package com.letung.parkinglot.feature.userListCar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.userListCar.adapter.CarAdapter
import com.letung.parkinglot.model.UserCar
import kotlinx.android.synthetic.main.activity_user_list_car.*

class UserListCarActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArraylist : ArrayList<UserCar>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list_car)

        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArraylist = arrayListOf<UserCar>()
        getUserData()
        eventListener()
    }

    private fun getUserData() {
        database = FirebaseDatabase.getInstance().getReference("Account/${Account.DATA_NAME}/userCar")
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(UserCar::class.java)
                        userArraylist.add(user!!)
                    }
                    userRecyclerview.adapter = CarAdapter(userArraylist)

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun eventListener(){
        imgbtn_backToMainActivity.setOnClickListener{
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}