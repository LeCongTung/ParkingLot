package com.letung.parkinglot.feature.userListParkingLot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.userListParkingLot.adapter.UserParkingSlotAdapter
import com.letung.parkinglot.model.UserParking
import kotlinx.android.synthetic.main.activity_user_list_parking.*
import java.util.*
import kotlin.collections.ArrayList

class UserListParkingActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArraylist : ArrayList<UserParking>
    private lateinit var tempArraylisr : ArrayList<UserParking>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list_parking)
        database = FirebaseDatabase.getInstance().getReference("Account/${Account.DATA_NAME}/userParkingSlot")
        userRecyclerView = findViewById(R.id.userParkingList)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        backToMainActivity()
        userArraylist = arrayListOf<UserParking>()
        tempArraylisr  = arrayListOf<UserParking>()
        getUserData()
        searchData()
    }

    private fun searchData() {
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
        tempArraylisr.clear()
        val searchText = newText!!.toLowerCase(Locale.getDefault())
        if(searchText.isNotEmpty()){
            userArraylist.forEach{
                if(it.position!!.toLowerCase(Locale.getDefault()).contains(searchText) ||
                    it.identityCar!!.toLowerCase(Locale.getDefault()).contains(searchText) ||
                    it.startTime!!.toLowerCase(Locale.getDefault()).contains(searchText) ||
                    it.hoursParking!!.toString().toLowerCase(Locale.getDefault()).contains(searchText) ||
                    it.totalPrice!!.toString().toLowerCase(Locale.getDefault()).contains(searchText)){
                    tempArraylisr.add(it)
                }
            }
            userRecyclerView.adapter!!.notifyDataSetChanged()
        }else{
            tempArraylisr.clear()
            tempArraylisr.addAll(userArraylist)
            userRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    private fun getUserData() {
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(userSnapshot in snapshot.children){
                        val user = userSnapshot.getValue(UserParking::class.java)
                        userArraylist.add(user!!)
                        tempArraylisr.add(user!!)
                    }
                    userRecyclerView.adapter = UserParkingSlotAdapter(tempArraylisr)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun backToMainActivity(){
        imgbtn_backToMainActivity.setOnClickListener(){
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}