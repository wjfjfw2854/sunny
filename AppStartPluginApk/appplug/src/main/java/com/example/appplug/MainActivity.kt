package com.example.appplug

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.libproxy.PluginBaseActivity

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
