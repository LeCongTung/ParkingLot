package com.letung.parkinglot.feature.userListCar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.userListCar.adapter.CarAdapter
import com.letung.parkinglot.model.UserCar
import kotlinx.android.synthetic.main.activity_user_list_car.*
import java.util.*
import kotlin.collections.ArrayList

class UserListCarActivity : AppCompatActivity(), CarAdapter.onItemClickListener {
    private lateinit var database : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArraylist : ArrayList<UserCar>
    private lateinit var tempArraylist : ArrayList<UserCar>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list_car)
        database = FirebaseDatabase.getInstance().getReference("Account/${Account.DATA_NAME}/userCar")
        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArraylist = arrayListOf<UserCar>()
        tempArraylist = arrayListOf<UserCar>()
        getUserData()
        eventListener()
        searchData()
    }

    private fun getUserData() {
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(UserCar::class.java)
                        userArraylist.add(user!!)
                        tempArraylist.add(user!!)
                    }
                    userRecyclerview.adapter = CarAdapter(tempArraylist, this@UserListCarActivity)

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun searchData(){
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })
    }

    private fun filterList(newText: String?) {
        tempArraylist.clear()
        val searchText = newText!!.toLowerCase(Locale.getDefault())
        if(searchText.isNotEmpty()){
            userArraylist.forEach {
                if(it.userCarName!!.toLowerCase(Locale.getDefault()).contains(searchText) ||
                    it.userCarNumber!!.toLowerCase(Locale.getDefault()).contains(searchText) ||
                    it.userCarType!!.toLowerCase(Locale.getDefault()).contains(searchText)){
                    tempArraylist.add(it)
                }
            }
            userRecyclerview.adapter!!.notifyDataSetChanged()
        }else{
            tempArraylist.clear()
            tempArraylist.addAll(userArraylist)
            userRecyclerview.adapter!!.notifyDataSetChanged()
        }
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

    override fun onItemClick(carName: String, carNumber: String, carType: String) {

    }
}