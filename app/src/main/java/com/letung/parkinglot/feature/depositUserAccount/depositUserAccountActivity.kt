package com.letung.parkinglot.feature.depositUserAccount

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.feature.depositUserAccount.adapter.BankAdapter
import com.letung.parkinglot.model.UserBank
import kotlinx.android.synthetic.main.activity_deposit_user_account.*
import kotlinx.android.synthetic.main.user_banklist.view.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.user_bottom_sheet.view.*
import java.util.*

class depositUserAccountActivity : AppCompatActivity(), BankAdapter.onItemClickListener {
    private lateinit var database: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArraylist: ArrayList<UserBank>
    private lateinit var tempArrayList: ArrayList<UserBank>
    private lateinit var dialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit_user_account)
        database = FirebaseDatabase.getInstance().getReference("Bank")

        userArraylist = arrayListOf<UserBank>()
        tempArrayList = arrayListOf<UserBank>()
        eventListener()
        backToUserProfile()
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
                    var adapter = BankAdapter(this@depositUserAccountActivity, tempArrayList, this@depositUserAccountActivity)
                    userRecyclerview.adapter = adapter
//                    adapter.setOnItemClickListener(object : BankAdapter.onItemClickListener{
//                        override fun onItemClick(position: Int) {
//                            Toast.makeText(this@depositUserAccountActivity, "$position", Toast.LENGTH_SHORT).show()
//                        }
//
//                    })
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
    private fun showBottomSheet() {
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val view = layoutInflater.inflate(R.layout.user_bottom_sheet, null)
        dialog.setCancelable(false)
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
        val searchBank = view.searchView
        val img_close = view.img_closeDialog
        //view.searchView
        searchData(searchBank)
        closeDialog(img_close)
    }

    private fun closeDialog(imgClose: ImageView) {
        imgClose.setOnClickListener(){
            tempArrayList.clear()
            dialog.dismiss()
        }
    }


    private fun searchData(searchBank: SearchView) {
        searchBank.clearFocus()
        searchBank.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
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
        tempArrayList.clear()
        val searchText = newText!!.toLowerCase(Locale.getDefault())
        if(searchText.isNotEmpty()){
            userArraylist.forEach{
                if(it.name!!.toLowerCase(Locale.getDefault()).contains(searchText)){
                    tempArrayList.add(it)
                }
            }
            userRecyclerview.adapter!!.notifyDataSetChanged()
        }else{
            tempArrayList.clear()
            tempArrayList.addAll(userArraylist)
            userRecyclerview.adapter!!.notifyDataSetChanged()
        }
    }


    private fun eventListener(){
        edt_bank.setOnClickListener(){
            showBottomSheet()
        }
    }

    private fun backToUserProfile() {
        imgbtn_backToMainActivity.setOnClickListener(){
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onItemClick(name: String, img: String) {
        dialog.dismiss()
        edt_bank.text = "khoa bu lon ${name}"
        Glide.with(this@depositUserAccountActivity).load(img).into(img_bank)
    }


}