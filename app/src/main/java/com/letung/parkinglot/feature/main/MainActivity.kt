package com.letung.parkinglot.feature.main

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.Account
import com.letung.parkinglot.feature.chooseUserCar.ChooseUserCarActivity
import com.letung.parkinglot.feature.parking.ParkingActivity
import com.letung.parkinglot.feature.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_newfeature_notice.*
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.letung.parkinglot.feature.main.adapter.ImageAdapter
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var handler : Handler
    private lateinit var imageList:ArrayList<Int>
    private lateinit var adapter: ImageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 3000)
            }
        })
        eventListener()
    }
    private fun eventListener(){
        img_userProfile.setOnClickListener(){
            startActivity(Intent(this, ProfileActivity::class.java))
        }
        car_parking.setOnClickListener(){
            startActivity(Intent(this, ChooseUserCarActivity::class.java))
        }
        car_rent.setOnClickListener(){
            setDialogNewFeatureNotice()
        }
        car_washing.setOnClickListener(){
            setDialogNewFeatureNotice()
        }
    }

    private fun setDialogNewFeatureNotice(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_newfeature_notice)
        if(dialog.window != null){
            dialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        dialog.setCancelable(false)
        dialog.show()
        dialog.btn_continueDismiss.setOnClickListener(){
            dialog.dismiss()
        }
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable , 3000)
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(50))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init(){
        viewPager2 = findViewById(R.id.viewPager2)
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()

        imageList.add(R.drawable.parkinglot1)
        imageList.add(R.drawable.parkinglot2)
        imageList.add(R.drawable.parkinglot3)
        imageList.add(R.drawable.parkinglot4)
        imageList.add(R.drawable.parkinglot5)

        adapter = ImageAdapter(imageList, viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 1
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

    }

}