package com.letung.parkinglot.model

data class Slot(
    val position: String ?= null,
    val status: String ? = null,
    val infoSlot: ArrayList<ParkingSlot> ?= null
)

data class ParkingSlot(
    val time: String ?= null,
    val check: Boolean ?= null,
    val idTicket: String ?= null
)
