package com.example.libproxy

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

/**
 *   author  : wujunfeng
 *   e-mail  : 421284553@qq.com
 *   date    : 2019/12/11 17:58
 *   version : 1.4.4
 */
object FileCopyUtil {

    fun copy2Dirs(context: Context,name : String) : String{
        context?.let {
            val dirTag : File = context!!.cacheDir
            if(!dirTag!!.exists())
            {
                dirTag!!.mkdirs()
            }
            val pathTag : File = File(dirTag,name)
            if(pathTag!!.exists())
            {
                pathTag!!.delete()
            }
            var input : InputStream ?= null
            var fileOutPut : FileOutputStream ?= null
            try {
                val res  : Boolean = pathTag.createNewFile()
                if(res)
                {
                    input = context!!.assets!!.open(name)
                    fileOutPut = FileOutputStream(pathTag)
                    val bf : ByteArray = ByteArray(input.available())
                    var bC : Int = -1
                    while ({bC = input.read(bf)
                            bC != -1}()){
                        fileOutPut.write(bf,0,bC)
                    }
                    Log.e("wjf>>>>","中间件apk已加载到了--" + pathTag + "--路径")
                    return pathTag.absolutePath
                }
                else
                {
                    Log.e("wjf>>>>","中间件apk加载失败--" + pathTag + "--路径")
                }
            }
            catch (e:Exception){
                e.printStackTrace()
            }
            finally {
                fileOutPut?.let {
                    fileOutPut!!.flush()
                }
                input?.let {
                    input!!.close()
                }
                fileOutPut?.let {
                    fileOutPut!!.close()
                }
            }
        }

        return "null"
    }

}