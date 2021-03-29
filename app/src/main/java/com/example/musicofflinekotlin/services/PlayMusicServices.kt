package com.example.musicofflinekotlin.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.utils.Constain
import com.example.musicofflinekotlin.utils.Notification
import com.example.musicofflinekotlin.utils.SharedPreferences
import java.io.IOException


class PlayMusicServices : Service() {
    private var mNotification: Notification? = null
    private var mSharedPreferences: SharedPreferences? = null
    private var mList: List<Song>? = null
    private var mPosition = 0

    companion object{
        var mMediaPlayer: MediaPlayer? = null
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerReceiver(broadcastReceiver, IntentFilter(Constain.sendActionBroadCast))
        mPosition = intent!!.getIntExtra(Constain.keyPosition, 0)
        init()

        startForeground(Constain.NOTIFICATION_ID, mNotification!!.mNotification)
        return START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        mNotification = Notification()
        mNotification!!.createNotification(this)

        mSharedPreferences = SharedPreferences(this)
        mList = mSharedPreferences!!.getSongList()

        starMediaPlayWithUri(mPosition)
    }


    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer!!.stop()
        unregisterReceiver(broadcastReceiver)
    }

    var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Constain.sendActionBroadCast) {
                if (intent.hasExtra(Constain.keyAction)) {
                    var action = intent.getStringExtra(Constain.keyAction)
                    when (action) {
                        Constain.keyActionPlay -> if (mMediaPlayer!!.isPlaying) mMediaPlayer!!.pause() else mMediaPlayer!!.start()
                        Constain.keyActionNext -> {
                            mPosition = if (mPosition >= mList!!.size - 1) 0 else ++mPosition
                            starMediaPlayWithUri(mPosition)
                        }
                        Constain.keyActionPrevious -> {
                            mPosition =
                                if (mPosition >= 1 && mList!!.size > 1) --mPosition else mList!!.size - 1
                            starMediaPlayWithUri(mPosition)
                        }
                        else -> Toast.makeText(
                            context,
                            getString(R.string.lbl_erro_song),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun starMediaPlayWithUri(position: Int) {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer!!.stop()
            }
            mMediaPlayer = MediaPlayer.create(this, Uri.parse(mList!![position].mPath))
            mMediaPlayer!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, getString(R.string.lbl_erro_song), Toast.LENGTH_LONG).show()
        }
    }
}