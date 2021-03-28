package com.example.musicofflinekotlin.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.musicofflinekotlin.room.table.Song

@Dao
interface SongDao {
    @Query("SELECT * FROM song")
   fun getListSong() : LiveData<List<Song>>

    @Query("SELECT * FROM song WHERE playList = 1")
    fun getListPlayList() : LiveData<List<Song>>

    @Query("SELECT * FROM song WHERE favourite = 1")
    fun getListMyFavourite() : LiveData<List<Song>>

    @Query("DELETE FROM song")
    fun deleteAll()

    @Insert
    fun insert(songList: List<Song>)

    @Delete
    fun delete(song: Song)

    @Update
    fun update(song: Song)
}