package com.example.musicofflinekotlin.repositories

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.musicofflinekotlin.room.dao.SongDao
import com.example.musicofflinekotlin.room.database.AppDatabase
import com.example.musicofflinekotlin.room.table.Song

class MainRepositories private constructor(){
    companion object{
        private var mMainRepositoriesInstance: MainRepositories? = null
        private var  mSongDao : SongDao? = null

        fun getInstance(context: Context): MainRepositories {
            if (mMainRepositoriesInstance == null) {
                mMainRepositoriesInstance = MainRepositories()
                mSongDao = AppDatabase.getInstance(context).songDao()
            }
            return mMainRepositoriesInstance!!
        }
    }

    fun deleteTableSong(){
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                mSongDao!!.deleteAll()
                return null
            }
        }.execute()
    }

    fun insertListSong(songList: ArrayList<Song>){
        object  : AsyncTask<Void?,Void?,Void?>(){
            override fun doInBackground(vararg params: Void?): Void?{
                mSongDao!!.insert(songList)
                return null
            }
        }.execute()
    }

    fun getListSong() : LiveData<List<Song>>{
        return mSongDao!!.getListSong()
    }

    fun updateSong(song: Song){
        object : AsyncTask<Void?,Void?,Void?>(){
            override fun doInBackground(vararg params: Void?): Void? {
                mSongDao!!.update(song)
                return null
            }
        }.execute()
    }

    fun deleteSong(song: Song){
        object : AsyncTask<Void?,Void?,Void?>(){
            override fun doInBackground(vararg params: Void?): Void? {
                mSongDao!!.delete(song)
                return null
            }
        }.execute()
    }

    fun getListPlayList() : LiveData<List<Song>>{
        return mSongDao!!.getListPlayList()
    }

    fun getListMyHistory() : LiveData<List<Song>>{
        return mSongDao!!.getListMyHistory()
    }

    fun searchSong(search : String?) : LiveData<List<Song>>{
        return mSongDao!!.searchSong(search)
    }
}