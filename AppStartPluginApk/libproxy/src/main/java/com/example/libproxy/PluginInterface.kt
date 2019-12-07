package com.example.libproxy

import android.app.Activity
import android.content.Intent
import android.os.Bundle

interface PluginInterface {

    fun attach(activity : Activity)
    fun onCreate(savedInstanceState : Bundle?)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
    fun onActivityResult(rc : Int,resC:Int,d:Intent?)

}