/*
 * Copyright 2020 Alex Almeida Tavella
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.com.core.tmdb.api

import br.com.moov.core.AppScope
import com.squareup.anvil.annotations.ContributesBinding
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject

@ContributesBinding(AppScope::class)
class TmdbRequestInterceptor @Inject constructor(
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
