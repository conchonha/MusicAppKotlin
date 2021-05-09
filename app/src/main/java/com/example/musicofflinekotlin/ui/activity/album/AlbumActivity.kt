package com.example.musicofflinekotlin.ui.activity.album

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseActivity
import com.example.musicofflinekotlin.callback.OnItemClickSongListener
import com.example.musicofflinekotlin.room.table.Song

class AlbumActivity : BaseActivity(), View.OnClickListener, OnItemClickSongListener {
    private var mAlbumViewModel: AlbumViewModel? = null
    private var mAdapter = AdapterRecyclerAlbum()

    override fun getContentView(): Int {
        return R.layout.activity_album
    }

    override fun onListenerClicked() {
        findViewById<ImageView>(R.id.mImgBack).setOnClickListener(this)
    }

    override fun onInit() {
        initRecyclerView()
    }

    override fun initViewModel() {
        mAlbumViewModel =
            ViewModelProvider(this, MyApplication.Holder.factory!!)[AlbumViewModel::class.java]

        mAlbumViewModel!!.getListAlbum().observe(this, Observer { songList ->
            Log.d("initViewModel", "initViewModel: ${songList.size}")
            mAdapter.setupData(songList, this)
        })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.mImgBack -> finish()
        }
    }

    private fun initRecyclerView() {
        var recycler = findViewById<RecyclerView>(R.id.mRecyclerViewAlbum)
        recycler.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@AlbumActivity,2 )
            adapter = mAdapter
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun clickOpenItem(songList: List<Song>, position: Int) {

    }

    override fun clickDeleteItem(songList: List<Song>, position: Int) {

    }
}