package com.letung.parkinglot.feature.userListCar.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.letung.parkinglot.R
import com.letung.parkinglot.feature.chooseUserCar.ChooseUserCarActivity
import com.letung.parkinglot.feature.depositUserAccount.adapter.BankAdapter
import com.letung.parkinglot.feature.userListCar.UserListCarActivity
import com.letung.parkinglot.model.UserCar
import kotlinx.android.synthetic.main.user_carlist.view.*

class CarAdapter(private val userList: ArrayList<UserCar>,
                 var listener: onItemClickListener) : RecyclerView.Adapter<CarAdapter.MyViewHolder>() {

    interface onItemClickListener{
        fun onItemClick(carName: String, carNumber: String, carType: String)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_carlist, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

//        holder.userCarName.text = currentitem.userCarName
        holder.itemView.tv_userCarName.text = currentitem.userCarName
        holder.itemView.tv_userCarNumber.text = currentitem.userCarNumber
        holder.itemView.tv_userCarType.text = currentitem.userCarType
        holder.itemView.item.setOnClickListener {
            listener.onItemClick(currentitem.userCarName!!, currentitem.userCarNumber!!, currentitem.userCarType!!)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){}
}