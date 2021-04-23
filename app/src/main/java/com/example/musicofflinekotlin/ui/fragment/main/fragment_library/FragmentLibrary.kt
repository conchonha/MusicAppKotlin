package com.example.musicofflinekotlin.ui.fragment.main.fragment_library

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseFragment
import com.example.musicofflinekotlin.ui.activity.favorite.FavoriteActivity
import com.example.musicofflinekotlin.ui.activity.main.MainViewModel
import com.example.musicofflinekotlin.ui.activity.music_list.MusicListActivity

class FragmentLibrary : BaseFragment(),View.OnClickListener {
    private var mView : View? = null
    private var mMainViewModel : MainViewModel? = null

    override fun initViewModel() {
        mMainViewModel = ViewModelProvider(activity!!,MyApplication.Holder.factory!!)[MainViewModel::class.java]
    }

    override fun init() {
    }

    override fun onListenerClicked() {
        mView!!.findViewById<CardView>(R.id.mCardSong).setOnClickListener(this)
        mView!!.findViewById<CardView>(R.id.mCardFavorite).setOnClickListener(this)
    }

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.layout_fragment_library,container,false)
        return mView
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.mCardSong -> startActivity(Intent(context,MusicListActivity::class.java))
            R.id.mCardFavorite->startActivity(Intent(context,FavoriteActivity::class.java))
        }
    }
}