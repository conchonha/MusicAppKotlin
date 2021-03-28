package com.example.musicofflinekotlin.ui.fragment.main.fragment_library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.base.BaseFragment

class FragmentLibrary : BaseFragment() {
    override fun initViewModel() {

    }

    override fun init() {
    }

    override fun onListenerClicked() {
    }

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.layout_fragment_library,container,false)
        return view
    }
}