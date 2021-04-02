package com.example.musicofflinekotlin.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.musicofflinekotlin.room.dao.SongDao
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.utils.Constain

@Database(entities = [Song::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun songDao(): SongDao

    companion object {
        private var mAppDatabase: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (mAppDatabase == null) {
                mAppDatabase = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,
                        Constain.APP_NAME_DATABASE).fallbackToDestructiveMigration().build()
            }
            return mAppDatabase!!
        }
    }
}