#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_gebilaolitou_ndkdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
//Java_gebilaolitou_ndkdemo_NDKTools_getStringFromNDK(
//        JNIEnv *env,
//        jobject /* this */) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}
//#ifndef _Included_gebilaolitou_ndkdemo_NDKTools
//#define _Included_gebilaolitou_ndkdemo_NDKTools
//#ifdef __cplusplus
//extern "C"{
//#endif
//JNIEXPORT jstring JNICALL Java_gebilaolitou_ndkdemo_NDKTools_getStringFromNDK
//        (JNIEnv *,jclass);
//#ifdef __cplusplus
//}
//#endif
//#endif
