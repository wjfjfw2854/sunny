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
jlong calcu2Arys(JNIEnv *env,jclass clazz,jobject arys);
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
            {"or","(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;",(void*)orString},
            {"calcXyz","(Ljava/util/ArrayList;)J",(void*)calcu2Arys}
    };
    jclass jClassName = (*env)->FindClass(env,"gebilaolitou/ndkdemo/libtools/ToolUtil");
    __android_log_print(ANDROID_LOG_DEBUG,"JNI_OnLoad","找到了jClassName");

    jint ret = (*env)->RegisterNatives(env,jClassName,method,2);
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

jlong calcu2Arys(JNIEnv *env,jclass clazz,jobject arys){
    jclass listClass = (*env)->GetObjectClass(env,arys);
    __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","listClass: ");
    jmethodID methGet = (*env)->GetMethodID(env,listClass,"get","(I)Ljava/lang/Object;");
    __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","methGet: ");
    jmethodID sizeD = (*env)->GetMethodID(env,listClass,"size","()I");
    __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","sizeD: ");
    jlong xMid;
    int i;
    jint size = (*env)->CallIntMethod(env,arys,sizeD);
    for(i = 0;i < size;i ++) {
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","i: ");
        jobject findDataElement = (*env)->CallObjectMethod(env,arys, methGet, i);
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","findDataElement: ");
        jclass dataElementClass = (*env)->GetObjectClass(env,findDataElement);
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","dataElementClass: ");

        jmethodID getX = (*env)->GetMethodID(env, dataElementClass, "getX", "()J");
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","getX: ");
        jlong x = (*env)->CallLongMethod(env, findDataElement, getX);
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","x: ");

        jmethodID getY = (*env)->GetMethodID(env, dataElementClass, "getY", "()J");
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","getY: ");
        jlong y = (*env)->CallLongMethod(env, findDataElement, getY);
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","y: ");

        jmethodID getZ = (*env)->GetMethodID(env, dataElementClass, "getZ", "()J");
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","getZ: ");
        jlong z = (*env)->CallLongMethod(env, findDataElement, getZ);
//        __android_log_print(ANDROID_LOG_DEBUG,"wjf_方法calcu2Arys","z: ");
        xMid += x * y * z;
    }
    return xMid;
}



