package com.example.appstartpluginapk

import androidx.multidex.MultiDexApplication
import com.example.libproxy.PluginBridger

class ApplicationM : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        PluginBridger.PluginX.instance.init(this)
    }

}