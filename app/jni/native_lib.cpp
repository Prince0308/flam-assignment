#include <jni.h>
#include <android/log.h>

#define LOG_TAG "native-lib"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_flam_assignment_NativeBridge_initNative(JNIEnv *, jobject, jobject, jint, jint) {
    LOGI("initNative (stub)");
    return JNI_TRUE;
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_flam_assignment_NativeBridge_processFrameDirect(JNIEnv *, jobject, jobject, jint, jint, jint, jint) {
    LOGI("processFrameDirect (stub)");
    return JNI_TRUE;
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_flam_assignment_NativeBridge_releaseNative(JNIEnv *, jobject) {
    LOGI("releaseNative (stub)");
    return JNI_TRUE;
}
