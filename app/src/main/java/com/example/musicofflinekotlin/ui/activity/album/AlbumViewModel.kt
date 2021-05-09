package com.example.musicofflinekotlin.ui.activity.album

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.musicofflinekotlin.repositories.MainRepositories
import com.example.musicofflinekotlin.room.table.Song

class AlbumViewModel(application: Application) : AndroidViewModel(application) {
    private var mMainRepositories : MainRepositories? = null

    init {
        mMainRepositories = MainRepositories.getInstance(application)
    }

    fun getListAlbum() : LiveData<List<Song>>{
        return mMainRepositories!!.getListAlbum()
    }
}