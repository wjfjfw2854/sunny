package com.example.libproxy

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import dalvik.system.DexClassLoader
import java.lang.Exception

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

    fun getPkgInfo() : PackageInfo{
        return packageInfo!!
    }

    fun getDexCLoader() : DexClassLoader{
        return dexClassLoader!!
    }

    fun getRe() : Resources{
        return res!!
    }

    fun get3Info(apkPath:String){
        c?.let {
            packageInfo = c!!.packageManager.getPackageArchiveInfo(apkPath,PackageManager.GET_ACTIVITIES)
            if(packageInfo == null)
            {
                throw RuntimeException("傀瘣的apkPath路径有误，请检查确认路径")
            }

            val dexFile = c!!.getDir("odex",Context.MODE_PRIVATE)
            dexClassLoader = DexClassLoader(apkPath,dexFile.absolutePath,null,c!!.classLoader)

            try {
                val assetM = AssetManager::class.java.newInstance()
                val method = AssetManager::class.java.getDeclaredMethod("addAssetPath",String::class.java)
                method.invoke(assetM,apkPath)
                res = Resources(assetM,c!!.resources.displayMetrics,c!!.resources.configuration)
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    fun gotoActivity(context : Context,name : String)
    {
        val intent : Intent = Intent(context,PuppentActivity::class.java)
        intent.putExtra(PluginKeyVal.CLASSNAME,name)
        context.startActivity(intent)
    }
}