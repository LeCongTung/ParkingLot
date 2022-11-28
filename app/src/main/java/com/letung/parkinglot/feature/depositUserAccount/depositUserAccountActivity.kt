package com.letung.parkinglot.feature.depositUserAccount

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.depositUserAccount.adapter.BankAdapter
import com.letung.parkinglot.model.UserBank
import kotlinx.android.synthetic.main.activity_deposit_user_account.*
import kotlinx.android.synthetic.main.user_banklist.view.*
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.user_bottom_sheet.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class depositUserAccountActivity : AppCompatActivity(), BankAdapter.onItemClickListener {
    private lateinit var database: DatabaseReference
    private lateinit var databaseMoney: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArraylist: ArrayList<UserBank>
    private lateinit var tempArrayList: ArrayList<UserBank>
    private lateinit var dialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit_user_account)
        database = FirebaseDatabase.getInstance().getReference("Bank")
        databaseMoney = FirebaseDatabase.getInstance().getReference("Account")
        userArraylist = arrayListOf<UserBank>()
        tempArrayList = arrayListOf<UserBank>()
        val userAccount = Account.DATA_NAME
        readData(userAccount)
        eventListener()
        backToUserProfile()
        checkConditionIDBank()
        checkConditionMoney()
        depositMoney()

    }

    private fun formatMoney(money: Int) : String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        return formatter.format(money)
    }

    private fun checkConditionIDBank(){
        edt_bankAccount.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(edt_bankAccount.text.toString().isEmpty()){
                    tv_remindIDBank.text = "Không được để trống"
                    tv_remindIDBank.visibility = View.VISIBLE
                }else{
                    if(edt_bankAccount.text.toString().length > 11){
                        tv_remindIDBank.text = "Nhập sai số tài khoản"
                        tv_remindIDBank.visibility = View.VISIBLE
                    }else{
                        tv_remindIDBank.visibility = View.GONE
                    }
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
    private fun checkConditionMoney(){
        edt_money.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(edt_money.text.toString().isEmpty()){
                    tv_remindDepositMoney.text = "Không được để trống"
                    tv_remindDepositMoney.visibility = View.VISIBLE
                }else
                    if(edt_money.text.toString().toInt() > 500000){
                        tv_remindDepositMoney.text = "Không được nạp quá 500.000đ"
                        tv_remindDepositMoney.visibility = View.VISIBLE
                    }else
                        if(edt_money.text.toString().toInt() < 30000){
                            tv_remindDepositMoney.text = "Không được nạp ít hơn 30.000đ"
                            tv_remindDepositMoney.visibility = View.VISIBLE
                        }else
                            tv_remindDepositMoney.visibility = View.GONE
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
    private fun readData(userAccount: String) {
        databaseMoney.child(userAccount).get().addOnSuccessListener {
            if(it.exists()){
                val userMoney = it.child("userMoney").value.toString().toInt()
                //tv_userMoney.text = "${formatMoney(userMoney)}" //đổi sang money
                tv_userMoney.text = "${userMoney}"
            }else{
                Toast.makeText(this, "Không tồn tại", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(){
            Toast.makeText(this, "Truy vấn thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private fun depositMoney(){

        btn_continueDepositMoney.setOnClickListener(){
            if(edt_money.text.toString().isEmpty() ||
                edt_bankAccount.text.toString().isEmpty() ||
                edt_money.text.toString().toInt() < 30000 ||
                edt_money.text.toString().toInt() > 500000 ||
                edt_bankAccount.text.toString().length > 11){
                Toast.makeText(this, "Chưa thỏa mãn các điều kiện", Toast.LENGTH_SHORT).show()

            }else{
                val userMoney = edt_money.text.toString().toInt()
                updateUserMoney(Account.DATA_NAME, userMoney)
            }
        }

    }

    private fun updateUserMoney(dataName: String, userMoney: Int) {
        val user = mapOf<String, Int>(
            "userMoney" to (userMoney + tv_userMoney.text.toString().toInt())
        )
        databaseMoney.child(dataName).updateChildren(user).addOnSuccessListener {
            tv_userMoney.text = "${userMoney + tv_userMoney.text.toString().toInt()}"
            edt_money.text.clear()
            edt_bankAccount.text.clear()
            edt_bank.text = ""
            tv_remindDepositMoney.visibility = View.GONE
            tv_remindIDBank.visibility = View.GONE
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener{
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        }
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
        dialog.setCancelable(true)
        dialog.setContentView(view)
        userRecyclerview = dialog.findViewById(R.id.bottom_bankList)!!
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArraylist = arrayListOf<UserBank>()
        getUserData()
        //set Height for dialog
        dialog.behavior.maxHeight = 1800
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
            tempArrayList.clear()
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
        edt_bank.text = "${name}"
        Glide.with(this@depositUserAccountActivity).load(img).into(img_bank)
    }


}