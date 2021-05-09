package com.example.musicofflinekotlin.ui.activity.album

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.callback.OnItemClickSongListener
import com.example.musicofflinekotlin.room.table.Song
import com.makeramen.roundedimageview.RoundedImageView

class AdapterRecyclerAlbum : RecyclerView.Adapter<AdapterRecyclerAlbum.AlbumViewHolder>() {
    private var mListSong: List<Song> = listOf()
    private var mOnItemClickSongListener: OnItemClickSongListener? = null

    fun setupData(
        songList: List<Song>,
        onItemClickSongListener: OnItemClickSongListener
    ) {
        mListSong = songList
        mOnItemClickSongListener = onItemClickSongListener
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        var view = View.inflate(parent.context,R.layout.item_list_album,null)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.mTxtTitle.text = mListSong[position].mTitle
        holder.mLinear2.setOnClickListener { mOnItemClickSongListener!!.clickOpenItem(songList = mListSong,position = position) }
    }

    override fun getItemCount(): Int {
        return mListSong.size
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTxtTitle = itemView.findViewById<TextView>(R.id.mTxtTitle)!!
        var mImgAvatar = itemView.findViewById<RoundedImageView>(R.id.mImgAvatar)!!
        var mLinear2 = itemView.findViewById<LinearLayout>(R.id.mLinear2)!!
    }
}