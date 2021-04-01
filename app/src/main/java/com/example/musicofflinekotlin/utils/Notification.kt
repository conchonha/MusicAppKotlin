package com.example.musicofflinekotlin.utils

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.room.table.Song
import com.example.musicofflinekotlin.ui.activity.playing_song.PlayingSongActivity

class Notification {
    private var mRemoteViews: RemoteViews? = null
    var mNotification: Notification? = null
    private var mSongList : List<Song>? = null
    private var mPndingIntent : PendingIntent? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(context: Context, icon: Int, position: Int, songList : String) {
        var intent = Intent(context, PlayingSongActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(Constain.keyPosition, position)
            putExtra(Constain.keySongList,songList)
        }

        mSongList = Helpers.getSongListFromString(songList)
        mPndingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mRemoteViews = RemoteViews(context.packageName, R.layout.layout_notification_mediaplayer)

        mRemoteViews!!.setImageViewResource(R.id.mImgPause, icon)
        mRemoteViews!!.setImageViewUri(R.id.mImgAlbum, Uri.parse(mSongList!![position].mPathUriImage))
        mRemoteViews!!.setTextViewText(R.id.mTxtName,mSongList!![position].mArtist)
        mRemoteViews!!.setTextViewText(R.id.mTxtTitle,mSongList!![position].mTitle)
        mRemoteViews!!.setTextViewText(R.id.mTxtDuration,
            Helpers.getTimeMusicFromMediaplayerDuration(mSongList!![position].mDuration!!.toInt()))

        mRemoteViews!!.setOnClickPendingIntent(
            R.id.mImgPause,
            Helpers.senPendingIntent(Constain.keyActionPlay, context,1)
        )
        mRemoteViews!!.setOnClickPendingIntent(
            R.id.mImgNext,
            Helpers.senPendingIntent(Constain.keyActionNext, context,2)
        )
        mRemoteViews!!.setOnClickPendingIntent(
            R.id.mImgPrevious,
            Helpers.senPendingIntent(Constain.keyActionPrevious, context,3)
        )
        mRemoteViews!!.setOnClickPendingIntent(
            R.id.mImgClose,
            Helpers.senPendingIntent(Constain.keyActionClose, context,4)
        )


        mNotification = Notification.Builder(context, Constain.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music_note)
            .setStyle(Notification.DecoratedCustomViewStyle()) //cho phep cung cap bo cuc tuy chinh
            .setCustomContentView(mRemoteViews) // set bố cục thu gọn
            .setContentIntent(mPndingIntent)
            .setCustomBigContentView(mRemoteViews) // sét bố cục mở rộng
            .build()
    }

}