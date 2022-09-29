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
import java.util.*

class register_parkinglot : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0

    var day_endTime = 0
    var month_endTime = 0
    var year_endTime = 0
    var hour_endTime = 0
    var minute_endTime = 0

    var savedDay_endTime = 0
    var savedMonth_endTime = 0
    var savedYear_endTime = 0
    var savedHour_endTime = 0
    var savedMinute_endTime = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_parkinglot)


        pickDate()
        pickDate_endTime()
    }

    private fun getDateTimeCalendar(){
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)


    }

    private fun getDateTimeCalendar_endTime(){
        val cal = Calendar.getInstance()
        day_endTime = cal.get(Calendar.DAY_OF_MONTH)
        month_endTime = cal.get(Calendar.MONTH)
        year_endTime = cal.get(Calendar.YEAR)
        hour_endTime = cal.get(Calendar.HOUR)
        minute_endTime = cal.get(Calendar.MINUTE)


    }

    private fun pickDate(){
        edt_startTime.setOnClickListener{
            getDateTimeCalendar()
            DatePickerDialog(this, this, year, month, day).show()
        }
    }

    private fun pickDate_endTime(){
        edt_endTime.setOnClickListener{
            getDateTimeCalendar_endTime()
            DatePickerDialog(this, this, year_endTime, month_endTime, day_endTime).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month + 1
        savedYear = year
        getDateTimeCalendar()
        getDateTimeCalendar_endTime()
        TimePickerDialog(this, this, hour, minute, true).show()

    }


    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        edt_startTime.setText("$savedHour" + "h" + "$savedMinute - $savedDay/ $savedMonth/ $savedYear")
    }
}