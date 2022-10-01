package com.letung.parkinglot.feature.parking.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.letung.parkinglot.R
import com.letung.parkinglot.model.Slot
import kotlinx.android.synthetic.main.item_slot.view.*

class ParkingAdapter(
    var context: Context,
    var listener: onSlotListener,
    var parkingSlotList: ArrayList<Slot>
): RecyclerView.Adapter<ParkingAdapter.MyViewHolder>() {
    interface onSlotListener{
        fun didClickSlot(item: Slot)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_slot, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = parkingSlotList[position]
        holder.itemView.positionTextView.text = item.position.toString()
        when (item.status){
            "parking" -> {
                holder.itemView.iconCar.setColorFilter(context.resources.getColor(R.color.supportRed), PorterDuff.Mode.SRC_IN)
                holder.itemView.positionTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.supportRed))
            }
            "repair" -> {
                holder.itemView.iconCar.setColorFilter(context.resources.getColor(R.color.supportyellow), PorterDuff.Mode.SRC_IN)
                holder.itemView.positionTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.supportyellow))
            }
            else -> {
                holder.itemView.iconCar.setColorFilter(context.resources.getColor(R.color.supportBlue), PorterDuff.Mode.SRC_IN)
                holder.itemView.positionTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.supportBlue))
            }
        }

        holder.itemView.itemSlot.setOnClickListener {
            listener.didClickSlot(item)
        }
    }

    override fun getItemCount(): Int {
        return parkingSlotList.size
    }
}