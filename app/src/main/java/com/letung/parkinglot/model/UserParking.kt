package com.letung.parkinglot.model

data class UserParking(
    val idTicket: String ?= null,
//    val name: String ? = null,
//    val phoneNumber: String ? = null,
    val position: String ? = null,
    val identityCar: String ? = null,
    val startTime: String ? = null,
//    val identityCar: String ? = null,
//    val type: String ? = null,
    val hoursParking: Int ? = null,
    val totalPrice: Int ? = null,
)
