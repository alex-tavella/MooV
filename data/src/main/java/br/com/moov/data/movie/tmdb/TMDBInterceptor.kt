package br.com.moov.data.movie.tmdb

import java.util.Locale
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class TMDBRequestInterceptor @Inject constructor(
  private val apiKey: String,
  private val cacheDuration: Long
) : Interceptor {

  companion object {
    const val QUERY_PARAM_API_KEY = "api_key"
    const val QUERY_PARAM_LANGUAGE = "language"
    const val HEADER_PARAM_CACHE_CONTROL = "Cache-Control"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    val url = request.url.newBuilder()
        .addQueryParameter(QUERY_PARAM_API_KEY, apiKey)
        .addQueryParameter(QUERY_PARAM_LANGUAGE, Locale.getDefault().getLanguageString())
        .build()

    val newRequest = request.newBuilder()
        .url(url)
        .addHeader(HEADER_PARAM_CACHE_CONTROL, "public, max-age=$cacheDuration")
        .build()

    return chain.proceed(newRequest)
  }
}

fun Locale.getLanguageString(): String {

  val sb = StringBuilder()
  sb.append(language)

  if (!country.isNullOrBlank() && country.length == 2) {
    sb.append("-").append(country)
  }

  return sb.toString()
}
