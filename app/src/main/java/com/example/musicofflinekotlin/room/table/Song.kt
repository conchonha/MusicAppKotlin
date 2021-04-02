package com.example.musicofflinekotlin.room.table

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.musicofflinekotlin.room.Object
import java.io.Serializable

@Entity(tableName = "song")
class Song : Object,Serializable{
    @ColumnInfo(name = "path")
    var mPath : String? = null

    @ColumnInfo(name = "title")
    var mTitle : String? = null

    @ColumnInfo(name = "duration")
    var mDuration : String? = null

    @ColumnInfo(name = "idAlbum")
    var mIdAlbum : String? = null

    @ColumnInfo(name = "nameAlbum")
    var mNameAlbum : String? = null

    @ColumnInfo(name = "artist")
    var mArtist : String? = null

    @ColumnInfo(name = "pathUriImageAlbum")
    var mPathUriImage : String? = null

    @ColumnInfo(name = "history")
    var mHistory : Int? = null

    @ColumnInfo(name = "pathLyrics")
    var mPathLyrics: String? = null

    @ColumnInfo(name = "favourite")
    var mFavourite : Int? = null

    @ColumnInfo(name = "playList")
    var mPlayList : Int? = null

    constructor()

    constructor(updateTime: String, path : String, title : String,duration: String,idAlbum : String,
                nameAlbum : String,artist : String,pathUriImageAlbum : String,history : Int,pathLyrics : String,
                favourite : Int,playList : Int) : super(updateTime){
        this.mPath = path
        this.mTitle = title
        this.mDuration = duration
        this.mIdAlbum = idAlbum
        this.mNameAlbum = nameAlbum
        this.mArtist = artist
        this.mPathUriImage = pathUriImageAlbum
        this.mHistory = history
        this.mPathLyrics = pathLyrics
        this.mFavourite = favourite
        this.mPlayList = playList
    }

//    fun getPath() : String?{
//        return this.mPath
//    }
//
//    fun getTitle() : String?{
//        return this.mTitle
//    }
//
//    fun getDuration() :String?{
//        return this.mDuration
//    }
//
//    fun getIdAlbum() : String?{
//        return this.mIdAlbum
//    }
//
//    fun getNameAlbum() : String?{
//        return this.mNameAlbum
//    }
//
//    fun getArtist() : String?{
//        return this.mArtist
//    }
//
//    fun getPathUriImage() : String?{
//        return this.mPathUriImage
//    }
//
//    fun getLyrics() : String?{
//        return this.mLyrics
//    }
//
//    fun getPathLyrics() : String?{
//        return this.mPathLyrics
//    }
//
//    fun getFavourite() : Int?{
//        return this.mFavourite
//    }
//
//    fun getPlayList() : Int?{
//        return this.mPlayList
//    }

}