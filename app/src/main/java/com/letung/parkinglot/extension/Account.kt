package com.letung.parkinglot.extension

import android.app.Application
class Account : Application() {
    companion object{
        //code pass data
        var CODE_ISUSER = false
        val CODE_ID_TICKET = "CODE_ID_TICKET"
        val CODE_DATA_NAME = "CODE_DATA_NAME"
        var DATA_NAME = ""
        val CODE_DATA_PHONENUMBER = "CODE_DATA_PHONENUMBER"
        var DATA_PHONENUMBER = ""
        val CODE_DATA_IDENTITY = "CODE_DATA_IDENTITY"
        var DATA_IDENTITY = ""
        val CODE_DATA_PASSWORD = "CODE_DATA_PASSWORD"
        var DATA_PASSWORD = ""
        val CODE_DATA_ID = "CODE_DATA_ID"
        var DATA_ID = ""
        val CODE_DATA_IDCAR = "CODE_DATA_IDCAR"
        var DATA_IDCAR = ""
        val CODE_DATA_CARTYPE = "CODE_DATA_CARTYPE"
        var DATA_CARTYPE = ""
        val CODE_DATA_CARNAME = "CODE_DATA_CARNAME"
        var DATA_CARNAME = ""
        var DATA_POSITION = 0
        var DATA_PRICE = 0
        val CODE_DATA_USERMONEY = "CODE_DATA_USERMONEY"
        var DATA_USERMONEY = ""
    }
}