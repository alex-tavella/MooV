/*
 *     Copyright 2017 Alex Almeida Tavella
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package br.com.alex.moov.data.tmdb

import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale

class TMDBRequestInterceptor(val apiKey: String, val cacheDuration: Int) : Interceptor {

  companion object {
    val QUERY_PARAM_API_KEY = "api_key"
    val QUERY_PARAM_LANGUAGE = "language"
    val HEADER_PARAM_CACHE_CONTROL = "Cache-Control"
  }

  override fun intercept(chain: Interceptor.Chain): Response {
    val request = chain.request()

    val url = request.url().newBuilder()
        .addQueryParameter(QUERY_PARAM_API_KEY, apiKey)
        .addQueryParameter(QUERY_PARAM_LANGUAGE, Locale.getDefault().language)
        .build()

    val newRequest = request.newBuilder()
        .url(url)
        .addHeader(HEADER_PARAM_CACHE_CONTROL, "public, max-age=$cacheDuration")
        .build()

    return chain.proceed(newRequest)
  }
}