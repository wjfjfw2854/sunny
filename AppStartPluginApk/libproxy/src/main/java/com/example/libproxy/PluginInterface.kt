package com.example.libproxy

import android.app.Activity
import android.content.Intent
import android.os.Bundle

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
interface PluginInterface {

    fun attach(activity : Activity)
    fun onCreate(savedInstanceState : Bundle?)
    fun onNewIntent(intent : Intent?)
    fun onStart()
    fun onResume()
    fun onPause()
    fun onStop()
    fun onDestroy()
    fun onActivityResult(rc : Int,resC:Int,d:Intent?)

}