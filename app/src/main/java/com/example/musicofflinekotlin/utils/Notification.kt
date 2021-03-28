package com.example.musicofflinekotlin.utils

import android.content.Context
import android.widget.RemoteViews
import com.example.musicofflinekotlin.R
import android.app.Notification
import android.os.Build
import androidx.annotation.RequiresApi

class Notification {
    var mRemoteViews : RemoteViews? = null
    var mNotification : Notification ? = null;

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotification(context : Context ){
        mRemoteViews = RemoteViews(context.packageName, R.layout.layout_notification_mediaplayer)

        mNotification = Notification.Builder(context,Constain.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music_note)
            .setStyle(Notification.DecoratedCustomViewStyle()) //cho phep cung cap bo cuc tuy chinh
            .setCustomContentView(mRemoteViews) // set bố cục thu gọn
            .setCustomBigContentView(mRemoteViews) // sét bố cục mở rộng
            .build()
    }

}