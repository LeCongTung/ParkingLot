package com.letung.parkinglot.extension

import android.app.Application

class App: Application() {
    companion object{
//        code_pass_data
        val CODE_ID_TICKET = "CODE_ID_TICKET"
        val CODE_DATA_NAME = "CODE_DATA_NAME"
        var DATA_NAME = ""
        val CODE_DATA_PHONENUMBER = "CODE_DATA_PHONENUMBER"
        var DATA_PHONENUMBER = ""
        val CODE_DATA_ID = "CODE_DATA_ID"
        var DATA_ID = ""
        val CODE_DATA_IDCAR = "CODE_DATA_IDCAR"
        var DATA_IDCAR = ""
        val CODE_DATA_TYPE = "CODE_DATA_TYPE"
        var DATA_TYPE = ""
        val CODE_DATA_POSITION = "CODE_DATA_POSITION"
        var DATA_POSITION = 0
        val CODE_DATA_PRICE = "CODE_DATA_PRICE"
        var DATA_PRICE = 0
    }
}