package com.example.appstartpluginapk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.libproxy.FileCopyUtil
import com.example.libproxy.PluginBridger

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
class MainActivity : AppCompatActivity() {

    var handler : Handler ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler()
        handler!!.post(Runnable {
            PluginBridger.PluginX.instance.get3Info(FileCopyUtil.copy2Dirs(this@MainActivity,"appplug-debug.apk"))
        })
        findViewById<View>(R.id.bt).setOnClickListener {
         PluginBridger.PluginX.instance.gotoActivity(this@MainActivity,
             PluginBridger.PluginX.instance.getPkgInfo().activities[0].name)
        }
    }
}
