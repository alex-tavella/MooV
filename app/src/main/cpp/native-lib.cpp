#include <jni.h>

extern "C" JNIEXPORT jstring JNICALL
Java_br_com_alex_moov_data_tmdb_TMDBApiKeyHolder_getApiKey(JNIEnv *env) {
    return env->NewStringUTF("12893b65fa1774765a53d7b9a1b07b70");
}