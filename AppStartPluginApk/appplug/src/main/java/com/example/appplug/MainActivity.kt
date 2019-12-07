package com.example.appplug

import android.os.Bundle
import com.example.libproxy.PluginBaseActivity

class MainActivity : PluginBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
