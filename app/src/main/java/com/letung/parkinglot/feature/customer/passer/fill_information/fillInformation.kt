package com.letung.parkinglot.feature.customer.passer.fill_information

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.letung.parkinglot.R

class fillInformation : AppCompatActivity() {
    val carType = arrayOf("Xe 5 chỗ", "Xe 7 chỗ", "Xe 9 chỗ", "Xe 16 chỗ")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_information)

        val spinner = findViewById<Spinner>(R.id.spinner_carType)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, carType)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
}