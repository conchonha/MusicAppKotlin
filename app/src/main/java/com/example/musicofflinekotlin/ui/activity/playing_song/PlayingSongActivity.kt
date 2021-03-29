package com.example.musicofflinekotlin.ui.activity.playing_song

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseActivity
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.services.PlayMusicServices
import com.example.musicofflinekotlin.utils.Constain
import com.example.musicofflinekotlin.utils.Helpers
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_playing_song.*

class PlayingSongActivity : BaseActivity(), View.OnClickListener {
    private var mPlayingSongViewModel: PlayingSongViewModel? = null
    private var mSongList: List<Song>? = null
    private var mPosition: Int = 0
    private var mAdapter: PlayingSongViewpagerAdapter? = null
    private var mCheckPlayMedia = false
    private var mRunnable : Runnable? = null
    private var mHandler: Handler = Handler()

    override fun getContentView(): Int {
        return R.layout.activity_playing_song
    }

    override fun onListenerClicked() {
        mImgPause.setOnClickListener(this)
        mImgNext.setOnClickListener(this)
        mImgPrevious.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onInit() {
        handlingData()
        initSeekBar()
        setAdapterViewpager()
    }

    private fun initSeekBar() {
         mRunnable = Runnable {
            mSeekBar.max = PlayMusicServices.mMediaPlayer!!.duration
            mSeekBar.progress = PlayMusicServices.mMediaPlayer!!.currentPosition
            mSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                    Log.d("AAA", "initSeekBar: progress${i}")
                    Log.d("AAA", "initSeekBar: max${mSeekBar.max}")
                    if(mSeekBar.progress == mSeekBar.max){
                        onNextMusicPree()
                        Log.d("AAA", "initSeekBar: bang nhau")
                    }
                    if (b) {
                        PlayMusicServices.mMediaPlayer!!.seekTo(i)
                        mSeekBar.progress = i
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
//             Log.d("AAA", "initSeekBar: progress${mSeekBar.progress}")
//             Log.d("AAA", "initSeekBar: max${mSeekBar.max}")
//             if(mSeekBar.progress == mSeekBar.max){
//                 onNextMusicPree()
//                 Log.d("AAA", "initSeekBar: bang nhau")
//             }
            mHandler.postDelayed(mRunnable!!, 1000)
        }
        mHandler.postDelayed(mRunnable!!, 500)
    }

    private fun setAdapterViewpager() {
        mAdapter = PlayingSongViewpagerAdapter(supportFragmentManager, applicationContext)
        mViewPagerPlaying.adapter = mAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handlingData() {
        if (intent.hasExtra(Constain.keySongList) && intent.hasExtra(Constain.keyPosition)) {
            Helpers.stopService(this)

            mCheckPlayMedia = true
            val type = object : TypeToken<List<Song>>() {}.type
            var songList: String = intent.getStringExtra(Constain.keySongList).toString()

            mSongList = Gson().fromJson(songList, type)
            mPosition = intent.getIntExtra(Constain.keyPosition, 0)

            mPlayingSongViewModel!!.saveSongListSharedPreferences(songList)
            mPlayingSongViewModel!!.setDataSongMutableLive(mSongList!![mPosition])

            var intentServices = Intent(this, PlayMusicServices::class.java)
            intentServices.putExtra(Constain.keyPosition, mPosition)
            ContextCompat.startForegroundService(this, intentServices)
        }
    }

    override fun initViewModel() {
        mPlayingSongViewModel = ViewModelProvider(
            this,
            MyApplication.Holder.factory!!
        )[PlayingSongViewModel::class.java]
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mImgPause -> {
                mCheckPlayMedia = !mCheckPlayMedia
                mImgPause.setImageResource(if (mCheckPlayMedia) R.drawable.ic_pause_white else R.drawable.ic_play_white)
                sendBoardCast(Constain.keyActionPlay)
            }
            R.id.mImgNext -> {
                onNextMusicPree()
            }
            R.id.mImgPrevious -> {
                mCheckPlayMedia = true
                mImgPause.setImageResource(if (mCheckPlayMedia) R.drawable.ic_pause_white else R.drawable.ic_play_white)
                mPosition =
                    if (mPosition >= 1 && mSongList!!.size > 1) --mPosition else mSongList!!.size - 1
                mPlayingSongViewModel!!.setDataSongMutableLive(mSongList!![mPosition])
                sendBoardCast(Constain.keyActionPrevious)
            }
        }
    }

    private fun onNextMusicPree(){
        mCheckPlayMedia = true
        mImgPause.setImageResource(if (mCheckPlayMedia) R.drawable.ic_pause_white else R.drawable.ic_play_white)
        mPosition = if (mPosition >= mSongList!!.size - 1) 0 else ++mPosition
        mPlayingSongViewModel!!.setDataSongMutableLive(mSongList!![mPosition])
        sendBoardCast(Constain.keyActionNext)
    }

    private fun sendBoardCast(action: String) {
        var intent1 = Intent(Constain.sendActionBroadCast)
        intent1.putExtra(Constain.keyAction, action)
        sendBroadcast(intent1)
    }
}