package com.example.musicofflinekotlin.ui.fragment.playing_song

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.layou_fragment_lyric.*
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

class LyricFragment : BaseFragment() {
    private var mPlayingSongViewModel: PlayingSongViewModel? = null
    private var mSong: Song? = null
    private var mView: View? = null

    override fun initViewModel() {
        mPlayingSongViewModel = ViewModelProvider(
            activity!!,
            MyApplication.Holder.factory!!
        )[PlayingSongViewModel::class.java]

        mPlayingSongViewModel!!.getDataSongMutableLive()
            .observe(viewLifecycleOwner, Observer { song ->
                mSong = song
                readFileLyrics()
            })
    }

    override fun init() {

    }

    private fun readFileLyrics() {
        var file = File(mSong!!.mPathLyrics)
        var stringBuilder = StringBuilder()

        try {
            var bufferedReader = BufferedReader(FileReader(file))
            var line = bufferedReader.readLine()

            while (line != null) {
                stringBuilder.append(line.substring(line.lastIndexOf("]") + 1))
                stringBuilder.append("\n")
                line = bufferedReader.readLine()
            }
            bufferedReader.close()

            if (stringBuilder.toString() == "") {
                stringBuilder.append(getString(R.string.lbl_lyrics_null))
            }
            mTxtLyrics.text = stringBuilder.toString()
        } catch (e: IOException) {
            Log.d("AA", "readFileLyrics: ${e.message}")
        }
    }

    override fun onListenerClicked() {

    }

    override fun getContentView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.layou_fragment_lyric, container, false)
        return mView
    }

}