package com.example.musicofflinekotlin.ui.fragment.main.fragment_home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseFragment
import com.example.musicofflinekotlin.callback.OnItemClickSongListener
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.ui.activity.main.MainViewModel
import com.example.musicofflinekotlin.ui.activity.playing_song.PlayingSongActivity
import com.example.musicofflinekotlin.utils.Constain
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_fragment_home.*

class FragmentHome : BaseFragment(), OnItemClickSongListener {
    private var mMainViewModel: MainViewModel? = null
    private var mAdapter: AdapterRecyclerView? = null

    override fun initViewModel() {
        mMainViewModel =
            ViewModelProvider(activity!!, MyApplication.Holder.factory!!)[MainViewModel::class.java]

        mMainViewModel!!.getListSong().observe(viewLifecycleOwner, Observer { songList ->
            setupRecyclerView(mRecyclerViewRecommended, songList)
        })

        mMainViewModel!!.getListPlayList().observe(viewLifecycleOwner, Observer { songList ->
            setupRecyclerView(mRecyclerViewMyPlayList, songList)
        })
    }

    override fun init() {

    }

    override fun onListenerClicked() {

    }

    override fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.layout_fragment_home, container, false)
        return view
    }

    override fun clickOpenItem(songList: List<Song>, position: Int) {
        var intent = Intent(context, PlayingSongActivity::class.java)

        intent.putExtra(Constain.keySongList, Gson().toJson(songList))
        intent.putExtra(Constain.keyPosition, position)

        startActivity(intent)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView, songList: List<Song>) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mAdapter = AdapterRecyclerView()
        recyclerView.adapter = mAdapter
        mAdapter!!.initData(songList, this)
        mAdapter!!.notifyDataSetChanged()
    }
}