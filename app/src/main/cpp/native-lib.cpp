#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_br_com_alex_moov_TMDBApiKeyHolder_getApiApiReadAccessToken(JNIEnv *env, jobject obj) {
    return env->NewStringUTF(
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMjg5M2I2NWZhMTc3NDc2NWE1M2Q3YjlhMWIwN2I3MCIsInN1YiI6IjU4N2ZmZDQwYzNhMzY4MzEyYzAwMDEyZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.r4xGHTusbKffGnRQjm6w1Z4EEeNfmLYcDAN3SPPriu8");
}

extern "C" JNIEXPORT jstring JNICALL
Java_br_com_alex_moov_TMDBApiKeyHolder_getApiKey(JNIEnv *env, jobject obj) {
    return env->NewStringUTF("12893b65fa1774765a53d7b9a1b07b70");
}