package com.example.musicofflinekotlin.ui.fragment.main.fragment_home

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.callback.OnItemClickSongListener
import com.example.musicofflinekotlin.room.table.Song
import com.makeramen.roundedimageview.RoundedImageView
import com.squareup.picasso.Picasso

class AdapterRecyclerView : RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> (){
    private var mSongList : List<Song>? = listOf()
    private var mOnItemClickSongListener : OnItemClickSongListener? = null

    fun initData( songList: List<Song> , onItemClickSongListener: OnItemClickSongListener){
        mSongList = songList
        mOnItemClickSongListener = onItemClickSongListener
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = View.inflate(parent.context,R.layout.item_list_music_home,null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var song = mSongList!![position]

        holder.mTxtNameSong.text = song.mTitle
        holder.mTxtSinger.text = song.mArtist
        Picasso.get().load(song.mPathUriImage).error(R.drawable.img_music_error).into(holder.mImgAvatar)

        holder.mImgAvatar.setOnClickListener(View.OnClickListener { mOnItemClickSongListener!!.clickOpenItem(mSongList!!,position) })
    }

    override fun getItemCount(): Int {
        return mSongList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         var mTxtNameSong : TextView = itemView.findViewById(R.id.mTxtNameSong)
         var mTxtSinger : TextView = itemView.findViewById(R.id.mTxtSinger)
         var mImgAvatar : RoundedImageView = itemView.findViewById(R.id.mImgAvatar)
    }
}