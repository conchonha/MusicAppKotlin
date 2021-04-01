package com.example.musicofflinekotlin.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.musicofflinekotlin.utils.Helpers

 open class Object {
    @PrimaryKey(autoGenerate = true)
     var mId : Int ? = null

    @ColumnInfo(name = "create_time")
     var mCreateTime : String ? = null

    @ColumnInfo(name = "update_time")
    var mUpdateTime : String ? = null

    constructor(updateTime : String ){
        mCreateTime = Helpers.getTimeToDay()
        mUpdateTime = updateTime
    }

    constructor()

//     fun setCreateTime(createTime : String){
//         this.mCreateTime = createTime
//     }
//
//     fun setUpdateTime(updateTime : String){
//         this.mUpdateTime = updateTime
//     }
//
//     fun getCreateTime () : String {
//         return this.mCreateTime!!
//     }
//
//     fun getUpdateTime() : String{
//         return this.mUpdateTime!!
//     }

}