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
package br.com.core.tmdb

import android.content.Context
import br.com.core.tmdb.api.TmdbApiKeyStore
import br.com.core.tmdb.image.ImageConfigurationApi
import br.com.moov.core.AppScope
import com.squareup.anvil.annotations.ContributesTo
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Qualifier
private annotation class BaseUrl

@Module(
    includes = [
        TmdbInternalModule::class
    ]
)
@ContributesTo(AppScope::class)
interface TmdbModule

private const val CACHE_SIZE: Long = 10 * 1024 * 1024

@Module
internal object TmdbInternalModule {
    @[Provides BaseUrl]
    fun providesBaseUrl(): String = "https://api.themoviedb.org"

    @Provides
    fun providesApiKey(apiKeyStore: TmdbApiKeyStore): String = apiKeyStore.getApiKey()

    @Provides
    fun providesCacheDuration(): Long = TimeUnit.DAYS.toSeconds(1)

    @Provides
    fun providesCache(context: Context): Cache = Cache(context.cacheDir, CACHE_SIZE)

    @Provides
    fun providesOkHttpClient(
        cache: Cache,
        interceptor: Interceptor
    ): OkHttpClient {
        val builder = OkHttpClient().newBuilder()

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }
        return builder
            .cache(cache)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        @BaseUrl baseUrl: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    fun providesImageConfigurationApi(retrofit: Retrofit): ImageConfigurationApi {
        return retrofit.create(ImageConfigurationApi::class.java)
    }
}
