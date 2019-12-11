package com.example.appstartpluginapk

import androidx.multidex.MultiDexApplication
import com.example.libproxy.PluginBridger

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
class ApplicationM : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        PluginBridger.PluginX.instance.init(this)
    }

}