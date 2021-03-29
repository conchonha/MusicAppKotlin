package com.example.musicofflinekotlin.ui.fragment.playing_song

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseFragment
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.ui.activity.playing_song.PlayingSongViewModel
import com.example.musicofflinekotlin.utils.Constain
import kotlinx.android.synthetic.main.layout_fragment_info.*
import kotlinx.android.synthetic.main.layout_fragment_info.view.*

class InfoFragment : BaseFragment(),View.OnClickListener{
    private var mPlayingSongViewModel : PlayingSongViewModel? = null
    private var mSong : Song? = null
    private var mView : View? = null

    override fun initViewModel() {
        mPlayingSongViewModel = ViewModelProvider(activity!!,MyApplication.Holder.factory!!)[PlayingSongViewModel::class.java]

        mPlayingSongViewModel!!.getDataSongMutableLive().observe(viewLifecycleOwner, Observer {
            song-> mSong = song
                   updateUi(mSong)

        })
    }

    private fun updateUi(song: Song?) {
        mImgInfo.setImageURI(Uri.parse(Constain.pathUriImgAlbum+song!!.mIdAlbum))
        mTxtName.text = song.mTitle
        mTxtSinger.text = song.mArtist
        mImgFavorite1.setImageResource(if(song.mFavourite == 1) R.drawable.ic_favorite_white else R.drawable.ic_favorite)
    }

    override fun init() {
        mView!!.findViewById<View>(R.id.mImgFavorite1).setOnClickListener(this)
    }


    override fun onListenerClicked() {

    }

    override fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.layout_fragment_info,container,false)
        return mView
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.mImgFavorite1->{
                mSong!!.mFavourite = if(mSong!!.mFavourite == 0) 1 else 0
                mPlayingSongViewModel!!.updateSong(mSong!!)
                mImgFavorite1.setImageResource(if(mSong!!.mFavourite == 1) R.drawable.ic_favorite_white else R.drawable.ic_favorite)
            }
        }
    }
}