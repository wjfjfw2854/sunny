package com.example.libproxy

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle
import java.util.*

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
class PuppentActivity : Activity() {
    var targetName : String ?= null
    var pluginInterface : PluginInterface ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        targetName = intent.getStringExtra(PluginKeyVal.CLASSNAME)
        try {
            val targetActivity : Class<PluginInterface> = PluginBridger.PluginX.instance.getDexCLoader().loadClass(targetName) as Class<PluginInterface>
            val obj = targetActivity.newInstance()
            if(obj is PluginInterface)
            {
                pluginInterface = obj
                val bud : Bundle = Bundle()
                bud.putInt(PluginKeyVal.ORIGIN,PluginKeyVal.EXTERNAL)
                pluginInterface?.let {
                    pluginInterface!!.attach(this@PuppentActivity)
                    pluginInterface!!.onCreate(bud)
                }
            }
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    override fun getClassLoader() : ClassLoader{
        val cLoader : ClassLoader = PluginBridger.PluginX.instance.getDexCLoader()
        return cLoader ?: super.getClassLoader()
    }

    override fun getResources(): Resources {
        val res : Resources = PluginBridger.PluginX.instance.getRe()
        return res ?: super.getResources()
    }

    override fun onStart() {
        pluginInterface?.let {
            pluginInterface!!.onStart()
        }
        super.onStart()
    }

    override fun onResume() {
        pluginInterface?.let {
            pluginInterface!!.onResume()
        }
        super.onResume()
    }

    override fun onPause() {
        pluginInterface?.let {
            pluginInterface!!.onPause()
        }
        super.onPause()
    }

    override fun onStop() {
        pluginInterface?.let {
            pluginInterface!!.onStop()
        }
        super.onStop()
    }

    override fun onDestroy() {
        pluginInterface?.let {
            pluginInterface!!.onDestroy()
        }
        super.onDestroy()
    }
}