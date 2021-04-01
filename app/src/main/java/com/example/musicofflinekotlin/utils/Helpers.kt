package com.example.musicofflinekotlin.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.services.PlayMusicServices
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class Helpers {
    companion object{
        fun getTimeToDay() : String{
            var calendar = Calendar.getInstance()
            var year = calendar.get(Calendar.YEAR)
            var mounth = calendar.get(Calendar.MONTH) + 1;
            var day = calendar.get(Calendar.DATE)
            return "$year-$mounth-$day"
        }

        fun stopService(context : Context) {
            context.stopService(Intent(context, PlayMusicServices::class.java))
        }

        fun getTimeMusicFromMediaplayerDuration(duration: Int): String? {
            var timerLabel: String? = ""
            val min = duration / 1000 / 60
            val sec = duration / 1000 % 60
            timerLabel += "$min:"
            if (sec < 10) timerLabel += "0"
            timerLabel += sec
            return timerLabel
        }

        fun sendBoardCastServices(action: String, context: Context) {
            var intent1 = Intent(Constain.sendActionBroadCastServices)
            intent1.putExtra(Constain.keyAction, action)
            context.sendBroadcast(intent1)
        }

        fun senPendingIntent(action: String,context: Context,requestCode : Int) : PendingIntent{
            var intent1 = Intent(Constain.sendActionBroadCastServices)
            intent1.putExtra(Constain.keyAction, action)

            return PendingIntent.getBroadcast(context,requestCode,intent1,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        fun getSongListFromString(songList: String?) : List<Song>?{
            var type = object : TypeToken<List<Song>>(){}.type
            return Gson().fromJson(songList,type)
        }
    }
}