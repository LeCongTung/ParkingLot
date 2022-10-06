package com.letung.parkinglot.feature.customer.passer.register_parkingLot

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.letung.parkinglot.R
import kotlinx.android.synthetic.main.activity_register_parkinglot.*
import android.text.format.DateFormat
import com.letung.parkinglot.feature.invoice.InvoiceActivity
import java.text.SimpleDateFormat
import java.util.*

class register_parkinglot : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    var day = 0
    var month: Int = 0
    var year: Int = 0
    var hour: Int = 0
    var minute: Int = 0
    var myDay = 0
    var myMonth: Int = 0
    var myYear: Int = 0
    var myHour: Int = 0
    var myMinute: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_parkinglot)

        pickDate()
        pickTime()
        pickHour()
        didClickToPickSlot()
    }

    private fun pickDate() {
        edt_day.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month, day)
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()
        }
    }

    private fun pickTime() {
        edt_time.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            hour = calendar.get(Calendar.HOUR)
            minute = calendar.get(Calendar.MINUTE)
            val timePickerDialog =
                TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this))
            timePickerDialog.show()
        }
    }

    private fun didClickToPickSlot(){
        btn_continue.setOnClickListener {
            val i = Intent(this, InvoiceActivity::class.java)
            startActivity(i)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pickHour() {
        var hourParking = tv_time.text.toString().toInt()
        val slotMoney = 30000
        var total = slotMoney
        tv_payment.text = "$slotMoney đồng/${hourParking} giờ"
        imgBtn_arrowUp_add.setOnClickListener {
            if (hourParking >= 48) {
                Toast.makeText(this, "Nhiều nhất là 48", Toast.LENGTH_SHORT)
                    .show()
            } else {
                hourParking++
                total = slotMoney * hourParking
                //Toast.makeText(this, "${hourParking}", Toast.LENGTH_SHORT).show()
            }
            tv_time.text = "$hourParking"
            tv_payment.text = "$total đồng/${hourParking} giờ"
        }

        imgBtn_muiltiArrowUp_add.setOnClickListener {
            if (hourParking >= 42) {
                Toast.makeText(this, "Nhiều nhất là 48 giờ", Toast.LENGTH_SHORT)
                    .show()
            } else {

                hourParking += 6
                total = slotMoney * hourParking
                //Toast.makeText(this, "${hourParking}", Toast.LENGTH_SHORT).show()
            }
            tv_time.text = "$hourParking"
            tv_payment.text = "$total đồng/${hourParking} giờ"
        }

        imgBtn_arrowDownBack_minus.setOnClickListener {
            if (hourParking == 1) {
                Toast.makeText(this, "Ít nhất là 1 giờ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                hourParking--
                total = slotMoney * hourParking
                //Toast.makeText(this, "${hourParking}", Toast.LENGTH_SHORT).show()
            }
            tv_time.text = "$hourParking"
            tv_payment.text = "$total đồng/${hourParking} giờ"
        }

        imgBtn_muiltiArrowUp_minus.setOnClickListener {
            if (hourParking <= 6) {
                Toast.makeText(this, "Ít nhất là 1 giờ", Toast.LENGTH_SHORT)
                    .show()
            } else {

                hourParking -= 6
                total = slotMoney * hourParking
                //Toast.makeText(this, "${hourParking}", Toast.LENGTH_SHORT).show()
            }
            tv_time.text = "$hourParking"
            tv_payment.text = "$total đồng/${hourParking} giờ"
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = dayOfMonth
        myYear = year
        myMonth = month + 1
        edt_day.setText("$myDay" + "/" + "$myMonth" + "/" + "$myYear")
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        val edt = "$myHour:$myMinute"
        if(!compareTwoTimes(getCurrentTime()!!,edt))
        {
            Toast.makeText(this, "Đặt chỗ bắt đầu từ thời gian hiện tại", Toast.LENGTH_SHORT).show()
            //edt_time.setText("Thời gian")
        }
        else  {
            edt_time.setText("$myHour" + "h" + "$myMinute")
        }


    }
    private fun getCurrentTime(): String? {
        val simpleDateFormat = SimpleDateFormat("hh:mm")
        return simpleDateFormat.format(Calendar.getInstance().time)
    }

    private fun compareTwoTimes(fromTime: String, currentTime : String): Boolean {
        val sdf = SimpleDateFormat("hh:mm")
        val time1 = sdf.parse(fromTime)
        val time2 = sdf.parse(currentTime)
        return !time2!!.before(time1)
    }

}