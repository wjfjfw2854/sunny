package com.example.appstartpluginapk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.libproxy.FileCopyUtil
import com.example.libproxy.PluginBridger

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
