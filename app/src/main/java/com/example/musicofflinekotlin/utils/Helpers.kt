package com.example.musicofflinekotlin.utils

import android.content.Context
import android.content.Intent
import com.example.musicofflinekotlin.services.PlayMusicServices
import java.util.*


class Helpers {
    fun getTimeToDay() : String{
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var mounth = calendar.get(Calendar.MONTH) + 1;
        var day = calendar.get(Calendar.DATE)
        return "$year-$mounth-$day"
    }

    companion object{
        fun stopService(context : Context) {
            context.stopService(Intent(context, PlayMusicServices::class.java))
        }
    }
}