package com.example.musicofflinekotlin.ui.fragment.main.fragment_search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseFragment
import com.example.musicofflinekotlin.callback.OnItemClickSongListener
import com.example.musicofflinekotlin.model.TextWatcher
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.ui.activity.main.MainViewModel
import com.example.musicofflinekotlin.ui.activity.playing_song.PlayingSongActivity
import com.example.musicofflinekotlin.utils.Constain
import com.google.gson.Gson
import kotlinx.android.synthetic.main.layout_fragment_search.*

class FragmentSearch : BaseFragment(), OnItemClickSongListener {
    private var mView: View? = null
    private var mMainViewModel: MainViewModel? = null
    private var mSongListHistory: List<Song>? = null
    private var mAdapter = AdapterRecycler()

    override fun initViewModel() {
        mMainViewModel = ViewModelProvider(activity!!, MyApplication.Holder.factory!!)[MainViewModel::class.java]

        mMainViewModel!!.getListMyHistory().observe(viewLifecycleOwner, Observer { songList ->
            mSongListHistory = songList
            mTxt1.visibility = VISIBLE
            mTxt2.visibility = VISIBLE
            initRecyclerView(mRecyclerViewHistory, songList, true)
        })
    }

    override fun init() {
        listenerEditText()
    }

    private fun listenerEditText() {
        mView!!.findViewById<EditText>(R.id.mEdtSearch)
            .addTextChangedListener(object : TextWatcher() {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    super.onTextChanged(s, start, before, count)
                    if (count == 0) {
                        Log.d("AAA", "onTextChanged: remove 0")
                        mTxt1.visibility = VISIBLE
                        mTxt2.visibility = VISIBLE
                        initRecyclerView(mRecyclerViewHistory, mSongListHistory!!, true)
                    } else {
                        mMainViewModel!!.searchSong(s.toString())
                            .observe(viewLifecycleOwner, Observer { songList ->
                                mTxt1.visibility = GONE
                                mTxt2.visibility = GONE
                                initRecyclerView(mRecyclerViewHistory, songList, false)
                            })
                        Log.d("AAA", "onTextChanged: remove 1")
                    }
                }
            })
    }

    override fun onListenerClicked() {}

    override fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.layout_fragment_search, container, false)
        return mView
    }

    private fun initRecyclerView(
        recyclerView: RecyclerView,
        songList: List<Song>,
        boolean: Boolean
    ) {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(context, 1)

        mAdapter.setupData(songList, this, boolean)
        recyclerView.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }

    override fun clickOpenItem(songList: List<Song>, position: Int) {
        var song = songList[position]
        song.mHistory = 1
        mMainViewModel!!.updateSong(song)

        var intent = Intent(context, PlayingSongActivity::class.java)
        intent.putExtra(Constain.keySongList, Gson().toJson(songList))
        intent.putExtra(Constain.keyPosition, position)

        startActivity(intent)
    }

    override fun clickDeleteItem(songList: List<Song>, position: Int) {
        var song = songList[position]
        song.mHistory = 0
        mMainViewModel!!.updateSong(song)
    }
}