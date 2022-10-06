package com.letung.parkinglot.feature.parking

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.App
import com.letung.parkinglot.feature.customer.passer.fill_information.fillInformation
import com.letung.parkinglot.feature.customer.passer.register_parkingLot.register_parkinglot
import com.letung.parkinglot.feature.detail.DetailActivity
import com.letung.parkinglot.feature.parking.adapter.ParkingAdapter
import com.letung.parkinglot.model.Slot
import kotlinx.android.synthetic.main.activity_fill_information.*
import kotlinx.android.synthetic.main.activity_parking.*
import kotlinx.android.synthetic.main.dialog_floor_area.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ParkingActivity : AppCompatActivity(), ParkingAdapter.onSlotListener {

    private lateinit var slotDatabase: DatabaseReference
    private var slotArrayList: ArrayList<Slot> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)

        setUpController()
        setUpDatabase()
        fetchDataSlot()
    }

    private fun setUpController(){
        currentTimeTextView.text = "Truy cập lúc: ${getCurrentDate()}"
    }

    private fun setUpDatabase() {
        slotDatabase = FirebaseDatabase.getInstance().getReference("Slot/A/1")
    }

    private fun getCurrentDate(): String{
        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
        return current.format(formatter)
    }

    private fun fetchDataSlot() {
        slotDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    slotArrayList.clear()
                    for (productSnapshot in snapshot.children) {
                            val item = productSnapshot.getValue(Slot::class.java)
                        slotArrayList.add(item!!)
                    }
                    slotArrayList.sortByDescending { it.position }
                    slotArrayList.reverse()

                    list_item_slot.adapter = ParkingAdapter(this@ParkingActivity,this@ParkingActivity, slotArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ParkingActivity, "Error to fetch data $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun showDialog(item: Slot) {
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.dialog_floor_area)

        dialog.positionTextView.text = "Vị trí: ${item.position}"
        dialog.statusTextView.text = "Tình trạng: ${item.status}"
        var canPark: Boolean
        when (item.status){
            "parking" -> {
                dialog.statusTextView.setTextColor(ContextCompat.getColor(this, R.color.supportRed))
                dialog.statusTextView.text = "Đang đỗ"
                canPark = false
            }
            "repair" -> {
                dialog.statusTextView.setTextColor(ContextCompat.getColor(this, R.color.supportyellow))
                dialog.statusTextView.text = "Đang bảo trì"
                canPark = false
            }
            else -> {
                dialog.statusTextView.setTextColor(ContextCompat.getColor(this, R.color.supportBlue))
                dialog.statusTextView.text = "Còn trống"
                canPark = true
            }
        }
        dialog.priceTextView.text = "Giá tiền: 30.000 VNĐ"
        dialog.declineButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.acceptButton.setOnClickListener {
            if (canPark){
                App.DATA_POSITION = item.position!!
                App.DATA_PRICE = 30000
                val intent = Intent(this, DetailActivity::class.java)
                startActivityForResult(intent, 2)
            }else
                Toast.makeText(this@ParkingActivity, "Vui lòng chọn vị trí khác!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(App.CODE_DATA_NAME, App.DATA_NAME)
        intent.putExtra(App.CODE_DATA_PHONENUMBER, App.DATA_PHONENUMBER)
        intent.putExtra(App.CODE_DATA_ID, App.DATA_ID)
        intent.putExtra(App.CODE_DATA_IDCAR, App.DATA_IDCAR)
        intent.putExtra(App.CODE_DATA_TYPE, App.DATA_TYPE)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun didClickSlot(item: Slot) {
        showDialog(item)
        Log.d("tung","${item.position} - ${item.status}")
    }
}