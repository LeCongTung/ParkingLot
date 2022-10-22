package com.letung.parkinglot.feature.customer.passer.fill_information

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.App
import com.letung.parkinglot.feature.parking.ParkingActivity
import kotlinx.android.synthetic.main.activity_fill_information.*

class fillInformation : AppCompatActivity() {
    val carTypeList = arrayOf("Xe 5 chỗ", "Xe 7 chỗ", "Xe 9 chỗ", "Xe 16 chỗ")
    var typeChoice = "Xe 5 chỗ"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fill_information)

        val spinner = findViewById<Spinner>(R.id.spinner_carType)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, carTypeList)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                typeChoice = carTypeList[p2]
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        eventListener()
    }

    private fun checkCondition(): Boolean{
        if (edt_name.text.toString().isEmpty()){
            Toast.makeText(this, "Yêu cầu nhập đầy đủ tên", Toast.LENGTH_SHORT).show()
            return false
        }else
            if (edt_phone.text.toString().length != 10){
                Toast.makeText(this, "Yêu cầu nhập đúng số điện thoại", Toast.LENGTH_SHORT).show()
                return false
            }else
                if (edt_identityCar.text.toString().length != 8){
                    Toast.makeText(this, "Yêu cầu nhập đầy đủ biển số xe", Toast.LENGTH_SHORT).show()
                    return false
                }else
                    return if (edt_identity.text.toString().length == 9 || edt_identity.text.toString().length == 12){
                        true
                    }else{
                        Toast.makeText(this, "Yêu cầu nhập đúng số CCCD/CMND", Toast.LENGTH_SHORT).show()
                        false
                    }
    }

    private fun eventListener(){
        btn_continue.setOnClickListener {
            if (checkCondition()){
                App.DATA_NAME = edt_name.text.toString()
                App.DATA_PHONENUMBER = edt_phone.text.toString()
                App.DATA_ID = edt_identity.text.toString()
                App.DATA_IDCAR = edt_identityCar.text.toString()
                App.DATA_TYPE = typeChoice
                val intent = Intent(this, ParkingActivity::class.java)
                startActivityForResult(intent, 1)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            (requestCode == 1 && resultCode == Activity.RESULT_OK) -> {
                data?.let {
                    Log.d("tung123", "${App.DATA_NAME} ${App.DATA_PHONENUMBER} ${App.DATA_ID} ${App.DATA_IDCAR} ${App.DATA_TYPE} back")
                }
            }
        }
    }
}