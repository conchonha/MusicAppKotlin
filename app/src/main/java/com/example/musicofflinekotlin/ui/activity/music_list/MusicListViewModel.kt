package com.example.musicofflinekotlin.ui.activity.music_list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicofflinekotlin.repositories.MainRepositories
import com.example.musicofflinekotlin.room.table.Song

class MusicListViewModel(application: Application) : AndroidViewModel(application) {
    private var mMainRepositories : MainRepositories? = null
    private var mSongLiveData = MutableLiveData<Song>()

    init {
        mMainRepositories = MainRepositories.getInstance(application)
    }

    fun getListSong() : LiveData<List<Song>> {
        return mMainRepositories!!.getListSong()
    }

    fun updateSong(song: Song){
        mMainRepositories!!.updateSong(song)
    }

    fun deleteSong(song: Song){
        mMainRepositories!!.deleteSong(song)
    }

    fun setDataSongMutableLive(song: Song){
        mSongLiveData.value = song
    }

    fun getDataSongMutableLive() : LiveData<Song>{
        return mSongLiveData
    }

}