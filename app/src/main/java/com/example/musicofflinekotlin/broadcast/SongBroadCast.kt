package com.example.musicofflinekotlin.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.musicofflinekotlin.callback.OnListenerBroadCast
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.utils.Constain
import com.example.musicofflinekotlin.utils.Helpers

class SongBroadCast : BroadcastReceiver() {
    private var mPosition = 0
    private var mSongList : List<Song> = listOf()
    private var mOnListenerBroadCast: OnListenerBroadCast? = null

    fun setUpdate(onListenerBroadCast: OnListenerBroadCast) {
        mOnListenerBroadCast = onListenerBroadCast
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        var action: String? = "hhh"

        if (intent!!.action == Constain.sendActionBroadCastActivity) {
            mPosition = intent.getIntExtra(Constain.keyPosition, 0)
            mSongList = Helpers.getSongListFromString(intent.getStringExtra(Constain.keySongList))!!

            if (intent.hasExtra(Constain.keyAction)) {
                action = intent.getStringExtra(Constain.keyAction)
            }
            mOnListenerBroadCast!!.onListenerActionClose(mPosition,mSongList, action?: "kk")
        }
    }
}