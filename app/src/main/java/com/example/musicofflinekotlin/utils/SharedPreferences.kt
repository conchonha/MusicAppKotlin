package com.example.musicofflinekotlin.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.musicofflinekotlin.room.table.Song
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferences(context: Context){
    private var mSharedPreferences: SharedPreferences = context.getSharedPreferences("datalogin", Context.MODE_PRIVATE)
    private var mEditTor: SharedPreferences.Editor = mSharedPreferences!!.edit()
    private val mGson = Gson()
    private val TAG = "SharePrefs"

    fun saveSongList(songList : String){
        mEditTor.putString(Constain.keySongList,songList).commit()
    }

    fun getSongList() : List<Song>{
        var type = object : TypeToken<List<Song>>() {}.type
        return mGson.fromJson(mSharedPreferences.getString(Constain.keySongList,""),type)
    }

}