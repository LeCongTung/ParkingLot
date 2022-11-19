package com.letung.parkinglot.extension

import android.app.Application
class Account : Application() {
    companion object{
        //code pass data
        val CODE_DATA_NAME = "CODE_DATA_NAME"
        var DATA_NAME = ""
        val CODE_DATA_PHONENUMBER = "CODE_DATA_PHONENUMBER"
        var DATA_PHONENUMBER = ""
        val CODE_DATA_IDENTITY = "CODE_DATA_IDENTITY"
        var DATA_IDENTITY = ""
        val CODE_DATA_PASSWORD = "CODE_DATA_PASSWORD"
        var DATA_PASSWORD = ""
        val CODE_DATA_MONEY = "CODE_DATA_MONEY"
        var DATA_MONEY = ""
    }
}