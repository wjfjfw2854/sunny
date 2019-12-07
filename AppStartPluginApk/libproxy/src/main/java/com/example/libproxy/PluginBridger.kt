package com.example.libproxy

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.res.Resources
import dalvik.system.DexClassLoader

class PluginBridger {
    var c : Context ?= null
    var packageInfo : PackageInfo ?= null
    var dexClassLoader : DexClassLoader ?= null
    var res : Resources ?= null
    object PluginX{
        val instance : PluginBridger = PluginBridger()
    }

    fun init(c : Context){
        this@PluginBridger.c = c.applicationContext
    }

    fun gotoActivity(context : Context,name : String)
    {
        val intent : Intent = Intent(context,PuppentActivity::class.java)
        intent.putExtra(PluginKeyVal.CLASSNAME,name)
        context.startActivity(intent)
    }
}