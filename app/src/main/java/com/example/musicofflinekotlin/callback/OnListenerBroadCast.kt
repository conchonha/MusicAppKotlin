package com.example.musicofflinekotlin.callback

import com.example.musicofflinekotlin.room.table.Song

interface OnListenerBroadCast {
    fun onListenerActionClose(position : Int,songList: List<Song>,action:String)
}