package com.letung.parkinglot.feature.chooseUserCar

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.parking.ParkingActivity
import com.letung.parkinglot.feature.userListCar.adapter.CarAdapter
import com.letung.parkinglot.model.UserCar
import kotlinx.android.synthetic.main.activity_choose_user_car.*
import kotlinx.android.synthetic.main.dialog_choosecar.*
import java.util.*
import kotlin.collections.ArrayList

class ChooseUserCarActivity : AppCompatActivity(), CarAdapter.onItemClickListener {
    private lateinit var database : DatabaseReference
    private lateinit var databaseMoney : DatabaseReference
    private lateinit var userRecyclerView : RecyclerView
    private lateinit var userArraylist : ArrayList<UserCar>
    private lateinit var tempArrayList: ArrayList<UserCar>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user_car)
        database = FirebaseDatabase.getInstance().getReference("Account/${Account.DATA_NAME}/userCar")
        databaseMoney = FirebaseDatabase.getInstance().getReference("Account")
        userRecyclerView = findViewById(R.id.userCarList)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)

        userArraylist = arrayListOf<UserCar>()
        tempArrayList = arrayListOf<UserCar>()
        getUserCarData()
        searchData()
        setDataMoney(Account.DATA_NAME)
        backToMainAcitivity()

    }

    private fun getUserCarData() {
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(usersnapshot in snapshot.children){
                        val user = usersnapshot.getValue(UserCar::class.java)
                        userArraylist.add(user!!)
                        tempArrayList.add(user!!)
                    }
                    var adapter = CarAdapter(tempArrayList, this@ChooseUserCarActivity)
                    userRecyclerView.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    private fun searchData(){
        searchCarView.clearFocus()
        searchCarView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterLits(newText)
                return true
            }

        })
    }

    private fun filterLits(newText: String?) {
        tempArrayList.clear()
        var searchText = newText!!.toLowerCase(Locale.getDefault())
        if(searchText.isNotEmpty()){
            userArraylist.forEach{
                if(it.userCarName!!.toLowerCase(Locale.getDefault()).contains(searchText) ||
                    it.userCarType!!.toLowerCase(Locale.getDefault()).contains(searchText) ||
                    it.userCarNumber!!.toLowerCase(Locale.getDefault()).contains(searchText)){
                    tempArrayList.add(it)
                }
            }
            userRecyclerView.adapter!!.notifyDataSetChanged()
        }else{
            tempArrayList.clear()
            tempArrayList.addAll(userArraylist)
            userRecyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    private fun backToMainAcitivity(){
        imgbtn_backToMainActivity.setOnClickListener(){
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onItemClick(carName: String, carNumber: String, carType: String) {
        setChooseCarDialog(carName, carNumber, carType)
    }

    private fun setChooseCarDialog(carName: String, carNumber: String, carType: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_choosecar)
        if(dialog.window != null){
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.setCancelable(false)
        dialog.show()
        dialog.tv_carName.text = carName
        dialog.tv_carID.text = carNumber
        dialog.tv_carNumber.text = carType
        dialog.btn_continueChooseCar.setOnClickListener(){
            Account.DATA_IDCAR = carNumber
            Account.DATA_CARTYPE = carType

            Toast.makeText(this, "Thành công ${Account.DATA_USERMONEY}", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, ParkingActivity::class.java))
            dialog.dismiss()
        }
        dialog.btn_cancel.setOnClickListener(){
            dialog.dismiss()
        }
    }

    private fun setDataMoney(dataName: String) {
        databaseMoney.child(dataName).get().addOnSuccessListener {
            if(it.exists()){
                //val userMoney =
                Account.DATA_USERMONEY = "${it.child("userMoney").value.toString().toInt()}"
            }else{
                Toast.makeText(this, "Không tồn tại", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(){
            Toast.makeText(this, "Truy vấn thất bại", Toast.LENGTH_SHORT).show()
        }
    }


}