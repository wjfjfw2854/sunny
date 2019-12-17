//
// Created by wujunfeng on 2019/12/16.
//
#include "gebilaolitou_ndkdemo_NDKTools.h"
JNIEXPORT jstring JNICALL Java_gebilaolitou_ndkdemo_NDKTools_getStringFromNDK
        (JNIEnv *env,jobject obj){
    return (*env) -> NewStringUTF(env,"啊国功能中，native frist code历功啊为粗");
}

