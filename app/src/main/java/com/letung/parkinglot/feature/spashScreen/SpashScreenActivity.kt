package com.letung.parkinglot.feature.spashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.letung.parkinglot.R
import com.letung.parkinglot.feature.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SpashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)

        moveToActivity()
    }
//    Delay and move to another Activity
    private fun moveToActivity() {
//    Cách 1
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            startActivity(Intent(this@SpashScreenActivity, MainActivity::class.java))
        }
//    Cách 2
//        Handler().postDelayed({
//            startActivity(Intent(this@SpashScreenActivity, MainActivity::class.java))
//        }, 3000)
    }
}