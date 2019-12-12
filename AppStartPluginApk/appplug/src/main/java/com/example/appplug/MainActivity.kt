package com.example.appplug

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.libproxy.PluginBaseActivity
import kotlinx.android.synthetic.main.activity_main.view.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
class MainActivity : PluginBaseActivity() {

    var x : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.but).setOnClickListener {
            val intent : Intent = Intent(this@MainActivity.proxyActivity,SecondActivity::class.java)
            startActivity(intent)
        }
        x = 888
        val curTime : Long = System.currentTimeMillis()
        val d : Date = Date(curTime)
        var strDate : String ?= null
        try {

            val s : SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            strDate = s.format(d)
        }
        catch (e : Exception)
        {
            e.printStackTrace()
        }
        val str = "x: $x" + "当前类名: " + this@MainActivity.proxyActivity!!.localClassName + " 日期: " + strDate
        (findViewById<View>(R.id.txt1) as TextView).text = str
        findViewById<View>(R.id.txt2).setOnClickListener {
            (it as TextView).text = ("操作起来: " + (++x))
        }
    }
}
