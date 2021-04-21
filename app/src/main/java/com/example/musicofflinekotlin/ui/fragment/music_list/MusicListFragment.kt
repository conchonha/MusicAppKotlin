package com.example.musicofflinekotlin.ui.fragment.music_list

import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseFragment
import com.example.musicofflinekotlin.broadcast.SongBroadCast
import com.example.musicofflinekotlin.callback.OnListenerBroadCast
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.services.PlayMusicServices
import com.example.musicofflinekotlin.ui.activity.music_list.MusicListViewModel
import com.example.musicofflinekotlin.ui.activity.playing_song.PlayingSongActivity
import com.example.musicofflinekotlin.utils.Constain
import com.example.musicofflinekotlin.utils.Helpers
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_playing_song.mImgPause
import kotlinx.android.synthetic.main.layout_fragment_music_list.*

class MusicListFragment : BaseFragment(), OnListenerBroadCast, View.OnClickListener {
    private var mCheckServices = false
    private var mView: View? = null
    private var mMusicListViewModel: MusicListViewModel? = null
    private var mSongBroadCast = SongBroadCast()
    private var mPosition = 0

    companion object {
        var mRunnable: Runnable? = null
        var mHandler: Handler = Handler()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSongBroadCast.setUpdate(this)
        context!!.registerReceiver(
            mSongBroadCast,
            IntentFilter(Constain.sendActionBroadCastActivity)
        )
    }

    override fun initViewModel() {
        mMusicListViewModel = ViewModelProvider(
            activity!!,
            MyApplication.Holder.factory!!
        )[MusicListViewModel::class.java]
    }

    override fun init() {
        getDataMusicIsPlay()
        onCompletionListener()
    }

    private fun getDataMusicIsPlay() {
        Helpers.sendBoardCastServices(
            Constain.keyActionMusicIsPlay,
            context!!
        )
    }

    private fun onCompletionListener() {
        PlayingSongActivity.mRunnable?.let {
            PlayingSongActivity.mHandler?.removeCallbacks(
                PlayingSongActivity.mRunnable!!
            )
        }
        mRunnable = Runnable {
            PlayMusicServices.mMediaPlayer?.let { it.setOnCompletionListener { onMusicNext() } }
        }
        mHandler.postDelayed(mRunnable!!, 500)
    }

    private fun onMusicNext() {
        Helpers.sendBoardCastServices(Constain.keyActionNext, context!!)
        onCompletionListener()
    }

    override fun onListenerClicked() {
        mView!!.findViewById<ImageView>(R.id.mImgPrevious).setOnClickListener(this)
        mView!!.findViewById<ImageView>(R.id.mImgPause).setOnClickListener(this)
        mView!!.findViewById<ImageView>(R.id.mImgNext).setOnClickListener(this)
    }

    override fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.layout_fragment_music_list, container, false)
        return mView
    }

    override fun onListenerActionClose(position: Int, songList: List<Song>, action: String) {
        mPosition = position
        mImgPause.setImageResource(if (PlayMusicServices.mMediaPlayer!!.isPlaying) R.drawable.ic_pause_white else R.drawable.ic_play_white)
        if (action == Constain.keyActionClose) {
            mCheckServices = true
            mImgPause.setImageResource(R.drawable.ic_play_white)
        }
        Picasso.get().load(songList[position].mPathUriImage).error(R.drawable.img_music_error)
            .into(mImgAvatar)
        mTxtTitle.text = songList[position].mTitle
        mTxtSinger.text = songList[position].mArtist
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(mSongBroadCast)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mImgPrevious -> Helpers.sendBoardCastServices(
                Constain.keyActionPrevious,
                context!!
            )
            R.id.mImgPause -> {
                if (mCheckServices) {
                    Helpers.onStartServices(context!!,mPosition,::onCompletionListener)
                    mCheckServices = false
                }
                mImgPause.setImageResource(if (PlayMusicServices.mMediaPlayer!!.isPlaying) R.drawable.ic_play_white else R.drawable.ic_pause_white)
                Helpers.sendBoardCastServices(Constain.keyActionPlay, context!!)
            }
            R.id.mImgNext -> Helpers.sendBoardCastServices(Constain.keyActionPrevious, context!!)
        }
    }
}