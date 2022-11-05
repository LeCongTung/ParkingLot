package com.letung.parkinglot.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.letung.parkinglot.R

class CarAdapter(private val userList : ArrayList<UserCar>) : RecyclerView.Adapter<CarAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_carlist, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.userCarName.text = currentitem.userCarName
        holder.userCarNumber.text = currentitem.userCarNumber
        holder.userCarType.text = currentitem.userCarType
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val userCarName : TextView = itemView.findViewById(R.id.tv_userCarName)
        val userCarNumber : TextView = itemView.findViewById(R.id.tv_userCarNumber)
        val userCarType : TextView = itemView.findViewById(R.id.tv_userCarType)
    }
}