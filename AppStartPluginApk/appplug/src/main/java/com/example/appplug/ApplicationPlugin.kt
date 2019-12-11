package com.example.appplug

import android.app.Application
import android.util.Log

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
class ApplicationPlugin : Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("wjf>>>>","ApplicationPlugin斯必达!方法onCreate()")
    }
}