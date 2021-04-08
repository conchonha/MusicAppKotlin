package com.example.musicofflinekotlin.ui.activity.music_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.musicofflinekotlin.repositories.MainRepositories
import com.example.musicofflinekotlin.room.table.Song

class MusicListViewModel(application: Application) : AndroidViewModel(application) {
    private var mMainRepositories : MainRepositories? = null

    init {
        mMainRepositories = MainRepositories.getInstance(application)
    }

    fun getListSong() : LiveData<List<Song>> {
        return mMainRepositories!!.getListSong()
    }
}