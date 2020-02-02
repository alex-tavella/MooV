package br.com.moov.data.common

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun <T> Call<T>.await(): T = suspendCoroutine {
  enqueue(object : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
      it.resumeWithException(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
      if (response.isSuccessful) {
        response.body()
            ?.apply { it.resume(this) }
            ?: it.resumeWithException(IllegalStateException("Response body is null"))
      } else {
        it.resumeWithException(IllegalStateException("Http error ${response.code()}"))
      }
    }
  })
}