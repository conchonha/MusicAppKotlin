package com.example.musicofflinekotlin.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.musicofflinekotlin.R

abstract class BaseFragment : Fragment() {
    private var mProgressDialog: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = getContentView(inflater,container,savedInstanceState)
        initViewModel()
        init()
        onListenerClicked()
        return view
    }

    protected abstract fun initViewModel()

    protected abstract fun init()

    protected abstract fun onListenerClicked()

    protected abstract fun getContentView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    protected fun showProgressLoadding() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
        mProgressDialog = ProgressDialog(activity)
        mProgressDialog!!.setIndeterminate(true)
        mProgressDialog!!.setMessage(getString(R.string.lbl_loading))
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog!!.show()
    }

    protected fun updateMessageProgressDialog(message: String?) {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.setMessage(message)
        }
    }

    protected fun dismisProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing()) {
            mProgressDialog!!.dismiss()
        }
    }

    protected fun isNetworkConnected(): Boolean {
        val connectivityManager = context!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfor = connectivityManager.activeNetworkInfo
        return activeNetInfor != null && activeNetInfor.isConnected
    }

    protected fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showAlertDialog(msg: String?) {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle(getString(R.string.dialog_box))
        dialogBuilder.setIcon(R.mipmap.ic_launcher)
        dialogBuilder.setMessage(msg)
        dialogBuilder.setPositiveButton("Ok") { dialog, which -> dialog.cancel() }
        dialogBuilder.setCancelable(false)
        dialogBuilder.show()
    }
}