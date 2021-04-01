package com.example.musicofflinekotlin.ui.activity.playing_song

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseActivity
import com.example.musicofflinekotlin.model.OnSeekBarChangeListener
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.services.PlayMusicServices
import com.example.musicofflinekotlin.utils.Constain
import com.example.musicofflinekotlin.utils.Helpers
import kotlinx.android.synthetic.main.activity_playing_song.*

class PlayingSongActivity : BaseActivity(), View.OnClickListener {
    private var mPlayingSongViewModel: PlayingSongViewModel? = null
    private var mSongList: List<Song>? = null
    private var mPosition: Int = 0
    private var mAdapter: PlayingSongViewpagerAdapter? = null
    private var mRunnable: Runnable? = null
    private var mHandler: Handler = Handler()
    private var mCheckServices = false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerReceiver(broadcastReceiver, IntentFilter(Constain.sendActionBroadCastActivity))
    }
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
        onCompletionListener()
    }

    private fun onCompletionListener() {
        var runnable = Runnable {
            mSeekBar.max = PlayMusicServices.mMediaPlayer!!.duration
            mTxtTimeDuration.text = Helpers.getTimeMusicFromMediaplayerDuration(PlayMusicServices.mMediaPlayer!!.duration)

            mSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener() {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean){
                    super.onProgressChanged(seekBar, progress, fromUser)
                    if (fromUser) {
                        PlayMusicServices.mMediaPlayer!!.seekTo(progress)
                        mSeekBar.progress = progress
                    }
                }
            })

            PlayMusicServices.mMediaPlayer!!.setOnCompletionListener {
                onMusicNext()
            }
        }
        Handler().postDelayed(runnable, 500)
    }

    private fun initSeekBar() {
        mRunnable = Runnable {
            mTxtTimeStar.text =
                Helpers.getTimeMusicFromMediaplayerDuration(PlayMusicServices.mMediaPlayer!!.currentPosition)
            mSeekBar.progress = PlayMusicServices.mMediaPlayer!!.currentPosition
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

            mSongList = Helpers.getSongListFromString(intent.getStringExtra(Constain.keySongList))
            mPosition = intent.getIntExtra(Constain.keyPosition, 0)

            mPlayingSongViewModel!!.saveSongListSharedPreferences(intent.getStringExtra(Constain.keySongList))
            mPlayingSongViewModel!!.setDataSongMutableLive(mSongList!![mPosition])

            onStartServices()
        }
    }

    private fun onStartServices() {
        var intentServices = Intent(this, PlayMusicServices::class.java)
        intentServices.putExtra(Constain.keyPosition, mPosition)
        ContextCompat.startForegroundService(this, intentServices)
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
                if(mCheckServices){
                    onStartServices()
                }
                mImgPause.setImageResource(if (PlayMusicServices.mMediaPlayer!!.isPlaying) R.drawable.ic_play_white else R.drawable.ic_pause_white)
                Helpers.sendBoardCastServices(Constain.keyActionPlay, this)
            }
            R.id.mImgNext -> onMusicNext()
            R.id.mImgPrevious -> {
                mImgPause.setImageResource(R.drawable.ic_pause_white)
                Helpers.sendBoardCastServices(Constain.keyActionPrevious, this)
            }
        }
    }

    private fun onMusicNext() {
        mImgPause.setImageResource(R.drawable.ic_pause_white)
        Helpers.sendBoardCastServices(Constain.keyActionNext, this)
        onCompletionListener()
    }

    private var broadcastReceiver : BroadcastReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            var action : String? = null
            if(intent!!.action == Constain.sendActionBroadCastActivity){
                mPosition = intent.getIntExtra(Constain.keyPosition,0)
                mPlayingSongViewModel!!.setDataSongMutableLive(mSongList!![mPosition])
                if(intent.hasExtra(Constain.keyAction)){
                    action = intent.getStringExtra(Constain.keyAction)
                    if(action == Constain.keyActionClose){
                        mCheckServices = true
                        mImgPause.setImageResource(R.drawable.ic_play_white)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
    }
}