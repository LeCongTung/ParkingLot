package com.letung.parkinglot.feature.depositUserAccount.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.letung.parkinglot.R
import com.letung.parkinglot.model.UserBank
import kotlinx.android.synthetic.main.user_banklist.view.*

class BankAdapter(val context: Context,
    private val userList: ArrayList<UserBank>,
    var listener : onItemClickListener) : RecyclerView.Adapter<BankAdapter.MyViewHolder>() {

    interface onItemClickListener{
        fun onItemClick(name: String, img: String)
    }

//    fun setOnItemClickListener(listener : onItemClickListener){
//        listener = listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_banklist, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        Glide.with(context).load(currentItem.bankLogoUrl).into(holder.itemView.img_bank)
        holder.itemView.tv_bankName.text = currentItem.name
        holder.itemView.item.setOnClickListener {
            listener.onItemClick(currentItem.name!!, currentItem.bankLogoUrl!!)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//        init{
//            itemView.setOnClickListener(){
//                listener.onItemClick(adapterPosition)
//            }
//        }
    }
}