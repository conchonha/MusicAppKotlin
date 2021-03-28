package com.example.musicofflinekotlin.ui.fragment.playing_song

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.base.BaseFragment

class LyricFragment : BaseFragment(){
    override fun initViewModel() {

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
        return inflater.inflate(R.layout.layou_fragment_lyric,container,false)
    }
}