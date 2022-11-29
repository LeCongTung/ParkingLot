package com.letung.parkinglot.feature.userListParkingLot.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.letung.parkinglot.R
import com.letung.parkinglot.model.UserParking
import kotlinx.android.synthetic.main.user_parkinglot_list.view.*
import java.text.DecimalFormat
import java.text.NumberFormat

class UserParkingSlotAdapter (
    private val userList : ArrayList<UserParking>):RecyclerView.Adapter<UserParkingSlotAdapter.MyViewHolder>() {

    private fun formatMoney(money : Int) : String{
        val formater : NumberFormat = DecimalFormat("#,###")
        return formater.format(money)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_parkinglot_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]
        holder.itemView.tv_IDTicket.text = currentitem.idTicket
        holder.itemView.tv_position.text = currentitem.position
        holder.itemView.tv_userIDCar.text = currentitem.identityCar
        holder.itemView.tv_startTime.text = currentitem.startTime
        holder.itemView.tv_hourParkingTotal.text = currentitem.hoursParking.toString()
        holder.itemView.tv_totalPrice.text = formatMoney(currentitem.totalPrice!!.toInt())
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){

    }

}