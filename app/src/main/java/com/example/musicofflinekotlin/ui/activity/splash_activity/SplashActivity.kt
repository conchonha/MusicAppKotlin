package com.example.musicofflinekotlin.ui.activity.splash_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.ui.activity.main.MainActivity

class SplashActivity : AppCompatActivity() {
    private var mRunnalble : Runnable? = null
    private var mHandler : Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        mHandler = Handler()
        mRunnalble = Runnable {
            startActivity(Intent(this,MainActivity::class.java))
        }
        mHandler!!.postDelayed(mRunnalble!!,2000)
    }
}