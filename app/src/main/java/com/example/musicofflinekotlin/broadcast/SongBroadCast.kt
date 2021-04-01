package com.example.musicofflinekotlin.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.musicofflinekotlin.utils.Constain

class SongBroadCast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if(intent!!.hasExtra(Constain.keyAction)){
            var intent1 = Intent(Constain.sendActionBroadCastServices)
            context!!.sendBroadcast(intent1)
        }
    }
}