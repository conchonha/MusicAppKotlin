package com.example.musicofflinekotlin.ui.activity.splash_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.base.BaseActivity
import com.example.musicofflinekotlin.ui.activity.main.MainActivity

class SplashActivity : BaseActivity() {
    private var mRunnalble : Runnable? = null
    private var mHandler : Handler? = null
    override fun getContentView(): Int {
       return R.layout.activity_splash
    }

    override fun onListenerClicked() {

    }

    override fun onInit() {
        mHandler = Handler()
        mRunnalble = Runnable {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        mHandler!!.postDelayed(mRunnalble!!,2000)
    }

    override fun initViewModel() {

    }


}