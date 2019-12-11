package com.example.appplug

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.libproxy.PluginBaseActivity

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
class MainActivity : PluginBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.but).setOnClickListener {
            val intent : Intent = Intent(this@MainActivity.proxyActivity,SecondActivity::class.java)
            startActivity(intent)
        }
    }
}
