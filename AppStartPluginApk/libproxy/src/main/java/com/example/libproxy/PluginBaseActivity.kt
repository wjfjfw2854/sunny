package com.example.libproxy

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
open class PluginBaseActivity : FragmentActivity(),PluginInterface {

    var origin :Int = PluginKeyVal.INNER
    var proxyActivity : Activity ?= null

    override fun setContentView(layoutResID: Int) {
        if(origin == PluginKeyVal.INNER) {
            super.setContentView(layoutResID)
        }
        else
        {
            proxyActivity?.let {
                proxyActivity!!.setContentView(layoutResID)
            }
        }
    }

    override fun <T : View?> findViewById(@IdRes id: Int): T {
        return if (origin == PluginKeyVal.INNER) {
                 super.findViewById<T>(id)
            } else {
                proxyActivity!!.findViewById<T>(id)
            }

    }

    override fun attach(activity: Activity) {
        proxyActivity = activity
    }


    override fun onCreate(@Nullable bundle : Bundle?) {
        bundle?.let {
            origin = bundle!!.getInt(PluginKeyVal.ORIGIN)
        }
        if(origin == PluginKeyVal.INNER)
        {
            super.onCreate(bundle)
            proxyActivity = this
        }
    }

    override fun onStart() {
        if(origin == PluginKeyVal.INNER) {
            super.onStart()
        }
        else
        {
            Log.e("wjf>>>>","来自壳的onStart()")
        }
    }

    override fun onResume() {
        if(origin == PluginKeyVal.INNER) {
            super.onResume()
        }
        else
        {
            Log.e("wjf>>>>","来自壳的onResume()")
        }
    }

    override fun onPause() {
        if(origin == PluginKeyVal.INNER) {
            super.onPause()
        }
        else
        {
            Log.e("wjf>>>>","来自壳的onPause()")
        }
    }

    override fun onStop(){
        if(origin == PluginKeyVal.INNER) {
            super.onStop()
        }
        else
        {
            Log.e("wjf>>>>","来自壳的onStop()")
        }
    }

    override fun onDestroy(){
        if(origin == PluginKeyVal.INNER) {
            super.onDestroy()
        }
        else
        {
            Log.e("wjf>>>>","来自壳的onDestroy()")
        }
    }

    override fun onActivityResult(rc : Int,resC:Int,@Nullable d :Intent?){
        if(origin == PluginKeyVal.INNER) {
            super.onActivityResult(rc,resC,d)
        }
        else
        {
            Log.e("wjf>>>>","来自壳的onActivityResult()")
        }
    }

    override fun startActivity(intent: Intent?) {
        if(origin == PluginKeyVal.INNER) {
            super.startActivity(intent)
        }
        else {
            intent?.let {
                PluginBridger.PluginX.instance.gotoActivity(
                    proxyActivity!!,
                    intent!!.component.className
                )
            }
        }
    }

}