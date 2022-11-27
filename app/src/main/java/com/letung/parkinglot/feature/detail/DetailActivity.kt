package com.letung.parkinglot.feature.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.extension.App
import com.letung.parkinglot.feature.invoice.InvoiceActivity
import com.letung.parkinglot.model.Invoice
import kotlinx.android.synthetic.main.activity_detail.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var databaseCar: DatabaseReference
    private lateinit var guestDatabase: DatabaseReference
    private lateinit var slotDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        database = FirebaseDatabase.getInstance().getReference("Account")
        databaseCar = FirebaseDatabase.getInstance().getReference("Account/${Account.DATA_NAME}/userCar")

        eventListener()
        if (Account.CODE_ISUSER){
            setUpInfomation()
        }
        setUpDetailInvoice()
        setUpDatabase()
        backToParkingLot()
    }

    private fun getStartTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH")
        return (current.format(formatter).toInt() + 1).toString()
    }

    private fun getCurrentDate(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return current.format(formatter)
    }

    private fun getCodeTicket(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm")

        return current.format(formatter) + App.DATA_IDCAR + current.format(formatter)
    }

    private fun formatMoney(money: Int): String {
        val formatter: NumberFormat = DecimalFormat("#,###")
        return formatter.format(money)
    }

    private fun setUpInfomation() {
        database.child(Account.DATA_NAME).get().addOnSuccessListener {
            if(it.exists()){
                //Account.DATA_NAME = it.child("userName").value.toString()
                Account.DATA_PHONENUMBER = it.child("userName").value.toString() //lỗi đặt tên biến => đảo tên biến
                Account.DATA_ID = it.child("userIdentity").value.toString()
                setUpDetailInvoice()
            }else{
                Toast.makeText(this, "Không tồn tại", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener(){
            Toast.makeText(this, "Truy vấn thất bại", Toast.LENGTH_SHORT).show()
        }
//        nameTextView.text = App.DATA_NAME
//        phoneNumberTextView.text = App.DATA_PHONENUMBER
//        identityTextView.text = App.DATA_ID
//        positionTextView.text = App.DATA_POSITION.toString()
//        identityCarTextView.text = App.DATA_IDCAR
//        typeTextView.text = App.DATA_TYPE
//        startTextView.text = "${getStartTime()} giờ (${getCurrentDate()})"
//        priceTextView.text =
//            "Tổng tiền: ${formatMoney(hourTextView.text.toString().toInt() * App.DATA_PRICE)} VNĐ"
    }

    private fun setUpDetailInvoice(){
        nameTextView.text = Account.DATA_PHONENUMBER //lỗi đặt tên biến => đảo tên biến
        phoneNumberTextView.text = Account.DATA_NAME //lỗi đặt tên biến => đảo tên biến
        identityTextView.text = Account.DATA_ID
        identityCarTextView.text = Account.DATA_IDCAR
        typeTextView.text = Account.DATA_CARTYPE
//        positionTextView.text = App.DATA_POSITION.toString()
//        identityCarTextView.text = App.DATA_IDCAR
//        typeTextView.text = App.DATA_TYPE
//        startTextView.text = "${getStartTime()} giờ (${getCurrentDate()})"
//        priceTextView.text =
//            "Tổng tiền: ${formatMoney(hourTextView.text.toString().toInt() * App.DATA_PRICE)} VNĐ"
    }

    private fun setUpDatabase() {
        guestDatabase = FirebaseDatabase.getInstance().getReference("Guest")
        slotDatabase = FirebaseDatabase.getInstance().getReference("Slot/1/A")
    }

    private fun updateStatus(){
        var updateStatus = mapOf<String, String>(
            "status" to "parking"
        )
        slotDatabase.child("A${App.DATA_POSITION}").updateChildren(updateStatus)
    }

    @SuppressLint("SetTextI18n")
    private fun eventListener() {
        multipleMinusButton.setOnClickListener {
            if (hourTextView.text.toString().toInt() > 6)
                hourTextView.text = "${hourTextView.text.toString().toInt() - 6}"
            else
                hourTextView.text = "1"
            if (hourTextView.text.toString().toInt() == 1)
                Toast.makeText(this, "Tối thiểu ít nhất 1 giờ", Toast.LENGTH_SHORT).show()
            priceTextView.text = "Tổng tiền: ${
                formatMoney(
                    hourTextView.text.toString().toInt() * App.DATA_PRICE
                )
            } VNĐ"
        }

        minusButton.setOnClickListener {
            if (hourTextView.text.toString().toInt() > 1) {
                hourTextView.text = "${hourTextView.text.toString().toInt() - 1}"
                priceTextView.text = "Tổng tiền: ${
                    formatMoney(
                        hourTextView.text.toString().toInt() * App.DATA_PRICE
                    )
                } VNĐ"
            } else
                Toast.makeText(this, "Tối thiểu ít nhất 1 giờ", Toast.LENGTH_SHORT).show()

        }

        plusButton.setOnClickListener {
            if (hourTextView.text.toString().toInt() < 48) {
                hourTextView.text = "${hourTextView.text.toString().toInt() + 1}"
                priceTextView.text = "Tổng tiền: ${
                    formatMoney(
                        hourTextView.text.toString().toInt() * App.DATA_PRICE
                    )
                } VNĐ"
            } else
                Toast.makeText(this, "Tối đa nhiều nhất 48 giờ", Toast.LENGTH_SHORT).show()

        }

        multiplePlusButton.setOnClickListener {
            if (hourTextView.text.toString().toInt() < 43)
                hourTextView.text = "${hourTextView.text.toString().toInt() + 6}"
            else
                hourTextView.text = "48"
            if (hourTextView.text.toString().toInt() == 48)
                Toast.makeText(this, "Tối đa nhiều nhất 48 giờ", Toast.LENGTH_SHORT).show()
            priceTextView.text = "Tổng tiền: ${formatMoney(hourTextView.text.toString().toInt() * App.DATA_PRICE)} VNĐ"
        }

        comfirmButton.setOnClickListener {
            val getID = getCodeTicket()
            comfirmButton.isEnabled = false
            val invoice = Invoice(
                getID,
                App.DATA_NAME,
                App.DATA_PHONENUMBER,
                App.DATA_ID,
                "A${App.DATA_POSITION}",
                App.DATA_IDCAR,
                App.DATA_TYPE,
                startTextView.text.toString(),
                hourTextView.text.toString().toInt(),
                hourTextView.text.toString().toInt() * App.DATA_PRICE
            )
            guestDatabase.child(getCodeTicket()).setValue(invoice).addOnCompleteListener {
                updateStatus()
                val intent = Intent(this, InvoiceActivity::class.java)
                intent.putExtra(App.CODE_ID_TICKET, getID)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun backToParkingLot(){
        imgbtn_backToParkingLot.setOnClickListener(){
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}