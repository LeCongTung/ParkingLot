package com.letung.parkinglot.feature.depositUserAccount.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.letung.parkinglot.R
import com.letung.parkinglot.model.UserBank
import kotlinx.android.synthetic.main.user_banklist.view.*
import java.net.URLEncoder

class BankAdapter(val context: Context,
    private val userList: ArrayList<UserBank>) : RecyclerView.Adapter<BankAdapter.MyViewHolder>() {

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.user_banklist, parent, false), mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
//        holder.itemView.img_bank.setImageURI(Uri.parse(currentItem.logo))
        Glide.with(context).load(currentItem.bankLogoUrl).into(holder.itemView.img_bank);
        holder.itemView.tv_bankName.text = currentItem.name
//        holder.itemView.tv_bankName.setOnClickListener {
//            Log.d("position", "$position")
//        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        init{
            itemView.setOnClickListener(){
                listener.onItemClick(adapterPosition)
            }
        }
    }
}