package com.example.musicofflinekotlin.ui.activity.main

import androidx.lifecycle.ViewModelProvider
import com.example.musicofflinekotlin.R
import com.example.musicofflinekotlin.app.MyApplication
import com.example.musicofflinekotlin.base.BaseActivity
import com.example.musicofflinekotlin.utils.ManagerMediaPlayer
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(){
    private var mMediaManagerMediaPlayer : ManagerMediaPlayer? = null
    private var mMainViewModel : MainViewModel? = null
    private var mMainViewPagerAdappter : MainViewpagerAdapter? = null;

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun onListenerClicked() {

    }

    override fun onInit() {
        initViewModel()
        initViewpager()
        setUpDataRoom()
    }

    override fun initViewModel() {
        mMainViewModel = ViewModelProvider(this, MyApplication.Holder.factory!!)[MainViewModel::class.java]
    }

    private fun initViewpager() {
        mMainViewPagerAdappter = MainViewpagerAdapter(supportFragmentManager, this)
        mViewPager.adapter = mMainViewPagerAdappter
        mTabLayout.setupWithViewPager(mViewPager)

        mTabLayout.getTabAt(0)!!.setIcon(R.drawable.ic_home)
        mTabLayout.getTabAt(1)!!.setIcon(R.drawable.ic_search)
        mTabLayout.getTabAt(2)!!.setIcon(R.drawable.ic_libary)
    }

    private fun setUpDataRoom() {
        mMediaManagerMediaPlayer = ManagerMediaPlayer(this)

        mMainViewModel!!.deleteTableSong()
        mMainViewModel!!.insertListSong(mMediaManagerMediaPlayer!!.getListSong())
    }
}