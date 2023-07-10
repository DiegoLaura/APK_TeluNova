package com.miempresa.ripter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class slidescreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slidescreen)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            val intent = Intent(this,welcome::class.java)
            startActivity(intent)
            finish()
        }, 1500
        )
    }
}