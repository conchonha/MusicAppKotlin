package com.example.musicofflinekotlin.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.musicofflinekotlin.R

abstract class BaseActivity : AppCompatActivity(){
    private var mRequestCode : Int = 100
    private var mProgressDialog : ProgressDialog ? = null
    private var mPermissionRequest = listOf<String>("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentView())
        if(!isPermissionGrant()){
            requestPermission()
        }
        initViewModel()
        onListenerClicked()
        onInit()
    }

    protected abstract fun getContentView() : Int

    protected abstract fun onListenerClicked()

    protected abstract fun onInit()

    protected abstract fun initViewModel()

    @RequiresApi(Build.VERSION_CODES.M)
    protected fun isPermissionGrant() : Boolean{
        for (permission in mPermissionRequest){
            if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                return false
            }
        }
        return true
    }

    protected fun requestPermission(){
        ActivityCompat.requestPermissions(this, mPermissionRequest.toTypedArray(),mRequestCode)
    }

    protected fun showAlertDialog(msg: String){
        var alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_box)
            alertDialog.setMessage(msg)
            alertDialog.setIcon(R.mipmap.ic_launcher)
            alertDialog.setPositiveButton(R.string.btn_ok, DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            alertDialog.setCancelable(false)
            alertDialog.show()
    }


    protected fun showProgressLoading(){
        if(mProgressDialog!!.isShowing){
            mProgressDialog!!.dismiss()
            mProgressDialog = null
        }
        mProgressDialog = ProgressDialog(this)
        mProgressDialog!!.setMessage(getString(R.string.lbl_loading))
        mProgressDialog!!.setCancelable(false)
        mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog!!.isIndeterminate = true
        mProgressDialog!!.show()
    }

    protected fun updateMessageProges(msg: String){
        if(mProgressDialog!!.isShowing){
            mProgressDialog!!.setMessage(msg)
        }
    }

    protected fun dismisProgess(){
        if(mProgressDialog!!.isShowing){
            mProgressDialog!!.dismiss()
        }
    }

    protected open fun isNetworkConnected(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfor = connectivityManager.activeNetworkInfo
        return activeNetInfor != null && activeNetInfor.isConnected
    }

    protected open fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected open fun hideKeyboard() {
        try {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            if (currentFocus != null) imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e)
        }
    }
}