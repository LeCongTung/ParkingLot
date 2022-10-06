package com.letung.parkinglot.feature.invoice

import android.Manifest
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import com.letung.parkinglot.R
import com.letung.parkinglot.extension.App
import kotlinx.android.synthetic.main.activity_invoice.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class InvoiceActivity : AppCompatActivity() {

    private lateinit var guestDatabase: DatabaseReference
    var idTicket: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invoice)

        setUpDatabase()
        setUpBill()
        setUpQRCode()
        eventListener()
    }

    private fun setUpQRCode(){
        val encoder = QRCodeWriter()
        try {
            val bitmapQRCode = encoder.encode(idTicket, BarcodeFormat.QR_CODE, 200, 200)
            val width = bitmapQRCode.width
            val height = bitmapQRCode.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width)
                for (y in 0 until height)
                    bmp.setPixel(x, y, if (bitmapQRCode[x, y]) Color.BLACK else Color.WHITE)
            qrCodeImage.setImageBitmap(bmp)
        }catch (e: WriterException){

        }
    }

    private fun setUpDatabase() {
        guestDatabase = FirebaseDatabase.getInstance().getReference("Guest")
    }

    private fun setUpBill(){
        idTicket = intent.getStringExtra(App.CODE_ID_TICKET).toString()
        Log.d("sos", idTicket)
        guestDatabase.child(idTicket).get().addOnSuccessListener {
            if (it.exists()){
                tv_name.text = it.child("name").value.toString().trim()
                tv_phone.text = it.child("phoneNumber").value.toString().trim()
                tv_id.text = it.child("identity").value.toString().trim()
                tv_licensePlate.text = it.child("identityCar").value.toString().trim()
                tv_regiserDay.text = it.child("startTime").value.toString().trim()
                tv_hourParking.text = it.child("hoursParking").value.toString().trim()
                tv_totalBill.text = it.child("totalPrice").value.toString().trim()
            }
        }
    }

    private fun eventListener(){
        screenShotButton.setOnClickListener {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            val bitmap = getScreenShotFromView(qrCodeImage)
            if (bitmap != null) {
                saveMediaToStorage(bitmap)
            }
        }
    }

    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "YourQR${System.currentTimeMillis()}.jpg"
        var fos: OutputStream? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }
                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Đã lưu vào thư viện" , Toast.LENGTH_SHORT).show()
        }
    }

    companion object Screenshot {
        private fun takeScreenshot(view: View): Bitmap {
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache(true)
            val b = Bitmap.createBitmap(view.drawingCache)
            view.isDrawingCacheEnabled = false
            return b
        }
        fun takeScreenshotOfRootView(v: View): Bitmap {
            return takeScreenshot(v.rootView)
        }
    }
}