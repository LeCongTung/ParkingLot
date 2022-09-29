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
    }

    private fun pickDate() {
        edt_day.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)
            val datePickerDialog =
                DatePickerDialog(this, this, year, month, day)
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

    private fun pickHour() {
//        var hourParking = 1
        var hourParking = tv_time.text.toString().toInt()
        imgBtn_arrowUp_add.setOnClickListener {
            if (hourParking >= 48) {
                Toast.makeText(this, "Nhiều nhất là 48", Toast.LENGTH_SHORT)
                    .show()
            } else {
                hourParking++
                //Toast.makeText(this, "${hourParking}", Toast.LENGTH_SHORT).show()
            }
            tv_time.text = "${hourParking}"
        }

        imgBtn_muiltiArrowUp_add.setOnClickListener {
            if (hourParking >= 42) {
                Toast.makeText(this, "Nhiều nhất là 48 giờ", Toast.LENGTH_SHORT)
                    .show()
            } else {

                hourParking += 6
                //Toast.makeText(this, "${hourParking}", Toast.LENGTH_SHORT).show()
            }
            tv_time.text = "${hourParking}"
        }

        imgBtn_arrowDownBack_minus.setOnClickListener {
            if (hourParking == 1) {
                Toast.makeText(this, "Ít nhất là 1 giờ", Toast.LENGTH_SHORT)
                    .show()
            } else {
                hourParking--
                //Toast.makeText(this, "${hourParking}", Toast.LENGTH_SHORT).show()
            }
            tv_time.text = "${hourParking}"
        }

        imgBtn_muiltiArrowUp_minus.setOnClickListener {
            if (hourParking <= 6) {
                Toast.makeText(this, "Ít nhất là 1 giờ", Toast.LENGTH_SHORT)
                    .show()
            } else {

                hourParking -= 6
                //Toast.makeText(this, "${hourParking}", Toast.LENGTH_SHORT).show()
            }
            tv_time.text = "${hourParking}"
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        myDay = day
        myYear = year
        myMonth = month + 1
////        val calendar: Calendar = Calendar.getInstance()
////        hour = calendar.get(Calendar.HOUR)
////        minute = calendar.get(Calendar.MINUTE)
//        val timePickerDialog = TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this))
//        timePickerDialog.show()
        edt_day.setText("$myDay" + "/" + "$myMonth" + "/" + "$myYear")

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        myHour = hourOfDay
        myMinute = minute
        edt_time.setText("$myHour" + "h" + "$myMinute")

    }
}