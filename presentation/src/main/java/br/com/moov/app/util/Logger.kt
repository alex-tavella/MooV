package br.com.moov.app.util

import android.util.Log
import br.com.moov.app.BuildConfig

const val LOG_TAG = "MooV"

inline fun logd(block: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.d(LOG_TAG, block())
    }
}

inline fun logw(block: () -> String) {
    if (BuildConfig.DEBUG) {
        Log.w(LOG_TAG, block())
    }
}
