package com.letung.parkinglot.feature.parking

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.*
import com.letung.parkinglot.R
import com.letung.parkinglot.feature.parking.adapter.ParkingAdapter
import com.letung.parkinglot.model.Slot
import kotlinx.android.synthetic.main.activity_parking.*
import kotlinx.android.synthetic.main.dialog_floor_area.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class ParkingActivity : AppCompatActivity(), ParkingAdapter.onSlotListener {

    private lateinit var slotDatabase: DatabaseReference
    private var slotArrayList: ArrayList<Slot> = arrayListOf()
    private val areaArrayList = arrayOf("A", "B", "C", "D")

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
                    list_item_slot.adapter = ParkingAdapter(this@ParkingActivity,this@ParkingActivity, slotArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ParkingActivity, "Error to fetch data $error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_floor_area)
        declineButton.setOnClickListener {
            dialog.dismiss()
        }
        acceptButton.setOnClickListener {

        }
        dialog.show()

    }

    override fun didClickSlot() {
        showDialog()
    }
}