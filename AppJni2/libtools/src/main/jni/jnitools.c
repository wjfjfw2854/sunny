//
// Created by wujunfeng on 2019/12/17.
//
#include <jni.h>
#include <android/log.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

jstring orString(JNIEnv *env,jclass clazz,jstring x1,jstring x2);
JNIEXPORT jint JNI_OnLoad(JavaVM* vm,void* reserved){
    __android_log_print(ANDROID_LOG_DEBUG,"JNI_OnLoad","动态进入JNI_OnLoad，很不错的开端！");
    JNIEnv* env = NULL;
    jint res = -1;

    if((*vm)->GetEnv(vm,(void**)&env,JNI_VERSION_1_6)!=JNI_OK)
    {
        __android_log_print(ANDROID_LOG_DEBUG,"JNI_OnLoad","GetEnv对比出错了，仔细检查！");
        return res;
    }
    const JNINativeMethod method[] = {
            {"or","(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",(void*)orString}
    };
    jclass jClassName = (*env)->FindClass(env,"gebilaolitou/ndkdemo/libtools/ToolUtil");
    __android_log_print(ANDROID_LOG_DEBUG,"JNI_OnLoad","找到了jClassName");

    jint ret = (*env)->RegisterNatives(env,jClassName,method,1);
    __android_log_print(ANDROID_LOG_DEBUG,"JNI_OnLoad","动态注册成功?ret: ");

    if(ret != JNI_OK)
    {
        __android_log_print(ANDROID_LOG_DEBUG,"JNI_OnLoad","动态注册失败ret: ");
        return -1;
    }

    return JNI_VERSION_1_6;
}


char* Jstring2CStr(JNIEnv* env, jstring jstr,jmethodID mid,jstring strencode) {
    char* rtn = NULL;
//    jclass clsstring = (*env)->FindClass(env,"java/lang/String");
//    jstring strencode = (*env)->NewStringUTF(env,"utf-8");//"GB2312");
//    jmethodID mid = (*env)->GetMethodID(env,clsstring,"getBytes","(Ljava/lang/String;)[B");
    jbyteArray barr= (jbyteArray)(*env)->CallObjectMethod(env,jstr,mid,strencode);
    jsize alen = (*env)->GetArrayLength(env,barr);
    jbyte* ba = (*env)->GetByteArrayElements(env,barr,JNI_FALSE);
    if(alen > 0) {
        rtn = (char*)malloc(alen+1); //"\0"
        memcpy(rtn,ba,alen);
        rtn[alen]=0;
    }
    (*env)->ReleaseByteArrayElements(env,barr,ba,JNI_FALSE);
    return rtn;
}

void freeMem(void *indicate)
{
    if(NULL != indicate)
    {
        free(indicate);
    }
}

jstring orString(JNIEnv *env,jclass clazz,jstring x1,jstring x2){
//    char* cstr1 = Jstring2CStr(env,x1);
//    char* cstr2 = Jstring2CStr(env,x2);
//    jstring str = (*env)->NewStringUTF(env,"-0-from*native垠比坤native*-0-");
//    char* mid = Jstring2CStr(env,str);


    jclass clsstring = (*env)->FindClass(env,"java/lang/String");
    jstring strencode = (*env)->NewStringUTF(env,"utf-8");//"GB2312");
    jmethodID bytes = (*env)->GetMethodID(env,clsstring,"getBytes","(Ljava/lang/String;)[B");
    char* cstr1 = Jstring2CStr(env,x1,bytes,strencode);
    char* cstr2 = Jstring2CStr(env,x2,bytes,strencode);
    jstring str = (*env)->NewStringUTF(env,"-0-from*native垠比坤native*-0-");
    char* mid = Jstring2CStr(env,str,bytes,strencode);

    jbyteArray barr1= (jbyteArray)(*env)->CallObjectMethod(env,x1,bytes,strencode);
    jbyteArray barr2= (jbyteArray)(*env)->CallObjectMethod(env,str,bytes,strencode);
    jbyteArray barr3= (jbyteArray)(*env)->CallObjectMethod(env,x2,bytes,strencode);
    jsize alen1 = (*env)->GetArrayLength(env,barr1);
    jsize alen2 = (*env)->GetArrayLength(env,barr2);
    jsize alen3 = (*env)->GetArrayLength(env,barr3);
    char* midChar=NULL;
    if(alen1 > 0 && alen2 > 0 && alen3 > 0)
    {
        midChar = (char*)malloc(alen1+alen2+alen3+3);
        if(midChar != NULL) {
            memset(midChar,0,alen1+alen2+alen3+3);
            strcpy(midChar,cstr1);
            strcat(midChar,mid);
            strcat(midChar,cstr2);
        }
    }
    freeMem(cstr1);
    freeMem(mid);
    freeMem(cstr2);

    jstring strFinal = (*env)->NewStringUTF(env,midChar);
    freeMem(midChar);
    return strFinal;
}



