package com.example.musicofflinekotlin.callback

import com.example.musicofflinekotlin.room.table.Song

interface OnItemClickSongListener {
    fun clickOpenItem(songList: List<Song>,position : Int)
    fun clickDeleteItem(songList: List<Song>,position : Int)
}