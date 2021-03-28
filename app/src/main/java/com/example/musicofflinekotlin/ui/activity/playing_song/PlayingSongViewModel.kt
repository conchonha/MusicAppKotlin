package com.example.musicofflinekotlin.ui.activity.playing_song

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.utils.SharedPreferences

class PlayingSongViewModel(application: Application) : AndroidViewModel(application) {
    private var mSongLiveData = MutableLiveData<Song>()
    private var mSharedPreferences = SharedPreferences(application)

    fun setDataSongMutableLive(song: Song){
        mSongLiveData.value = song
    }

    fun getDataSongMutableLive() : LiveData<Song>{
        return mSongLiveData
    }

    fun saveSongListSharedPreferences(songList : String){
        mSharedPreferences.saveSongList(songList)
    }
}