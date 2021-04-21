package com.example.musicofflinekotlin.ui.activity.music_list

import android.content.Intent
import android.os.Build
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseActivity
import com.example.musicofflinekotlin.callback.OnItemClickSongListener
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.services.PlayMusicServices
import com.example.musicofflinekotlin.ui.activity.playing_song.PlayingSongActivity
import com.example.musicofflinekotlin.ui.dialog.ShowBottomSheetDialog
import com.example.musicofflinekotlin.utils.Constain
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_music_list.*
import kotlinx.android.synthetic.main.activity_music_list.mRelativeBottom

class MusicListActivity : BaseActivity(),View.OnClickListener,OnItemClickSongListener {
    private var mMusicListViewModel : MusicListViewModel? = null
    private var mAdapter : AdapterRecycler = AdapterRecycler()
    private var mSongList: List<Song> = listOf()

    override fun initViewModel() {
        mMusicListViewModel = ViewModelProvider(this,MyApplication.Holder.factory!!)[MusicListViewModel::class.java]

        mMusicListViewModel!!.getListSong().observe(this, Observer { songList->
            mSongList = songList
            mAdapter.setupData(songList,this)
        })
    }

    override fun getContentView(): Int {
        return R.layout.activity_music_list
    }

    override fun onListenerClicked() {
        mImgBack.setOnClickListener(this)
        mCardViewPlayRandom.setOnClickListener(this)
    }

    override fun onInit() {
        initRecyclerView()
        updateUi()
    }

    private fun updateUi() {
        PlayingSongActivity.mRunnable?.let { PlayingSongActivity.mHandler?.removeCallbacks(it) }

        var runnable = Runnable {
            if(PlayMusicServices.mMediaPlayer == null){
                mRelativeBottom.visibility = GONE
            }else{
                mRelativeBottom.visibility = VISIBLE
            }
        }
       Handler().postDelayed(runnable,500)
    }

    private fun initRecyclerView() {
        var recycler = findViewById<RecyclerView>(R.id.mRecyclerViewMusicList)
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@MusicListActivity,1)
            adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.mImgBack -> finish()
            R.id.mCardViewPlayRandom -> {
                clickOpenItem(mSongList,mSongList.indices.random())
                PlayMusicServices.mRanDom = !PlayMusicServices.mRanDom
                mTxtTextRanDomMusic.text =
                    if (PlayMusicServices.mRanDom) getString(R.string.lbl_off_random) else getString(
                        R.string.lbl_play_random
                    )
            }
        }
    }

    override fun clickOpenItem(songList: List<Song>, position: Int) {
        Intent(this, PlayingSongActivity::class.java).apply {
            putExtra(Constain.keySongList, Gson().toJson(songList))
            putExtra(Constain.keyPosition, position)
            startActivityForResult(this,Constain.REQUEST_CODE)
        }
    }

    override fun clickDeleteItem(songList: List<Song>, position: Int) {
        ShowBottomSheetDialog().show(supportFragmentManager,Constain.keyBottomSheetDialog)
        mMusicListViewModel!!.setDataSongMutableLive(songList[position])
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Constain.REQUEST_CODE && resultCode == RESULT_OK){
            updateUi()
        }
    }
}