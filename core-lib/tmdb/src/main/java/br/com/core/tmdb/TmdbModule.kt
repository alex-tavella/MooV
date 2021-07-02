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
import br.com.core.tmdb.api.TmdbRequestInterceptor
import br.com.core.tmdb.image.AverageImageConfigurationProvider
import br.com.core.tmdb.image.ImageConfigurationApi
import br.com.core.tmdb.image.ImageConfigurationProvider
import br.com.core.tmdb.image.TmdbImageUrlResolver
import br.com.moov.core.AppScope
import br.com.moov.core.ImageUrlResolver
import com.squareup.anvil.annotations.ContributesTo
import dagger.Binds
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
import javax.inject.Singleton

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
internal interface TmdbInternalModule {

    companion object {
        @[Provides Singleton BaseUrl]
        fun providesBaseUrl() = "https://api.themoviedb.org"

        @[Provides Singleton]
        fun providesApiKey(apiKeyStore: TmdbApiKeyStore): String = apiKeyStore.getApiKey()

        @[Provides Singleton]
        fun providesCacheDuration(): Long = TimeUnit.DAYS.toSeconds(1)

        @[Provides Singleton]
        fun providesCache(context: Context): Cache = Cache(context.cacheDir, CACHE_SIZE)

        @[Provides Singleton]
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

        @[Provides Singleton]
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

        @[Provides Singleton]
        fun providesImageConfigurationApi(retrofit: Retrofit): ImageConfigurationApi {
            return retrofit.create(ImageConfigurationApi::class.java)
        }
    }
}
