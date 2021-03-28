package com.example.musicofflinekotlin.ui.activity.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.musicofflinekotlin.repositories.MainRepositories
import com.example.musicofflinekotlin.room.table.Song

class MainViewModel(application: Application) : AndroidViewModel(application){
    private var mMainRepositories : MainRepositories ? = null

    init {
        mMainRepositories = MainRepositories.getInstance(application)
    }

    fun deleteTableSong(){
        mMainRepositories!!.deleteTableSong()
    }

    fun insertListSong(songList : ArrayList<Song>){
        mMainRepositories!!.insertListSong(songList)
    }

    fun getListSong() : LiveData<List<Song>>{
        return mMainRepositories!!.getListSong()
    }

    fun getListPlayList() : LiveData<List<Song>>{
        return mMainRepositories!!.getListPlayList()
    }
}