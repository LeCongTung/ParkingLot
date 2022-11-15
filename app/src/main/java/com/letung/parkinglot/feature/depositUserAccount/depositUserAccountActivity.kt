package com.letung.parkinglot.feature.depositUserAccount

import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.feature.depositUserAccount.adapter.BankAdapter
import com.letung.parkinglot.model.UserBank
import kotlinx.android.synthetic.main.activity_deposit_user_account.*
import kotlinx.android.synthetic.main.user_banklist.*
import kotlinx.android.synthetic.main.user_bottom_sheet.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList
import androidx.core.view.contains

class depositUserAccountActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArraylist: ArrayList<UserBank>
    private lateinit var tempArrayList: ArrayList<UserBank>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit_user_account)
        database = FirebaseDatabase.getInstance().getReference("Bank")
        userArraylist = arrayListOf<UserBank>()
        tempArrayList = arrayListOf<UserBank>()
        eventListener()
        //searchData()
    }

    private fun getUserData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(UserBank::class.java)
                        userArraylist.add(user!!)
                        tempArrayList.add(user!!)
                        Log.d("tung123123", userSnapshot.toString())
                    }
                    var adapter = BankAdapter(this@depositUserAccountActivity, userArraylist)
                    userRecyclerview.adapter = adapter
                    adapter.setOnItemClickListener(object : BankAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            Toast.makeText(this@depositUserAccountActivity, "$position", Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun showBottomSheet() {
        val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.user_bottom_sheet, null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        userRecyclerview = dialog.findViewById(R.id.bottom_bankList)!!
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArraylist = arrayListOf<UserBank>()
        getUserData()
        //set Height for dialog
        dialog.behavior.maxHeight = 1500
        dialog.behavior.peekHeight = dialog.behavior.maxHeight
        dialog.show()
    }

    private fun searchData(){
    }

    private fun filterList(newText: String?) {
    }


    private fun eventListener(){
        edt_bank.setOnClickListener(){
            showBottomSheet()
        }
    }

}