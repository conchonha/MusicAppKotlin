package com.example.musicofflinekotlin.ui.activity.playing_song

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseActivity
import com.example.musicofflinekotlin.broadcast.SongBroadCast
import com.example.musicofflinekotlin.callback.OnListenerBroadCast
import com.example.musicofflinekotlin.model.OnSeekBarChangeListener
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.services.PlayMusicServices
import com.example.musicofflinekotlin.ui.fragment.music_list.MusicListFragment
import com.example.musicofflinekotlin.utils.Constain
import com.example.musicofflinekotlin.utils.Helpers
import kotlinx.android.synthetic.main.activity_playing_song.*

class PlayingSongActivity : BaseActivity(), View.OnClickListener, OnListenerBroadCast {
    private var mPlayingSongViewModel: PlayingSongViewModel? = null
    private var mSongList: List<Song>? = null
    private var mPosition: Int = 0
    private var mAdapter: PlayingSongViewpagerAdapter? = null
    private var mCheckServices = false
    private var mSongBroadCast = SongBroadCast()

    companion object {
        var mRunnable: Runnable? = null
        var mHandler: Handler = Handler()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSongBroadCast.setUpdate(this)
        registerReceiver(mSongBroadCast, IntentFilter(Constain.sendActionBroadCastActivity))
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
        MusicListFragment.mRunnable?.let {
            MusicListFragment.mHandler?.removeCallbacks(
               it
            )
        }
        var runnable = Runnable {
            mSeekBar.max = PlayMusicServices.mMediaPlayer!!.duration
            mTxtTimeDuration.text =
                Helpers.getTimeMusicFromMediaplayerDuration(PlayMusicServices.mMediaPlayer!!.duration)

            mSeekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener() {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
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

            Helpers.onStartServices(this,mPosition,::onCompletionListener)
        }
    }

    override fun initViewModel() {
        mPlayingSongViewModel = ViewModelProvider(
            this,
            MyApplication.Holder.factory!!
        )[PlayingSongViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mImgPause -> {
                if (mCheckServices) {
                    Helpers.onStartServices(this,mPosition,::onCompletionListener)
                    mCheckServices = false
                }
                mImgPause.setImageResource(if (PlayMusicServices.mMediaPlayer!!.isPlaying) R.drawable.ic_play_white else R.drawable.ic_pause_white)
                Helpers.sendBoardCastServices(Constain.keyActionPlay, this)
            }
            R.id.mImgNext -> onMusicNext()
            R.id.mImgPrevious -> Helpers.sendBoardCastServices(Constain.keyActionPrevious, this)

        }
    }

    private fun onMusicNext() {
        Helpers.sendBoardCastServices(Constain.keyActionNext, this)
        onCompletionListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mSongBroadCast)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            setResult(RESULT_OK)
            finish()
        }
        return true
    }

    override fun onListenerActionClose(position: Int, songList: List<Song>, action: String) {
        mImgPause.setImageResource(R.drawable.ic_pause_white)
        if (action == Constain.keyActionClose) {
            mCheckServices = true
            mImgPause.setImageResource(R.drawable.ic_play_white)
        }
        mPlayingSongViewModel!!.setDataSongMutableLive(songList[position])
    }
}