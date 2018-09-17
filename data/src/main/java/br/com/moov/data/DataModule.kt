package br.com.moov.data

import android.content.Context
import br.com.moov.BuildConfig
import br.com.moov.data.movie.MovieDataSource
import br.com.moov.data.movie.MovieEntityMapper
import br.com.moov.data.movie.MovieRepositoryImpl
import br.com.moov.data.movie.TMDBMovieDataSource
import br.com.moov.data.movie.tmdb.TMDBApiKeyStore
import br.com.moov.data.movie.tmdb.TMDBDApi
import br.com.moov.data.movie.tmdb.TMDBRequestInterceptor
import br.com.moov.domain.movie.MovieRepository
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import org.koin.dsl.module.module

fun dataModule(context: Context) = module {
  factory { createCache(context) }
  factory { TMDBApiKeyStore() }
  factory { createRequestInterceptor(get()) } bind Interceptor::class
  factory { createHttpClient(get(), get()) }
  single { TMDBDApi.create(get()) }
  single { TMDBMovieDataSource(get()) } bind MovieDataSource::class
  factory { MovieEntityMapper() }
  single { MovieRepositoryImpl(get(), get()) } bind MovieRepository::class
}

fun createRequestInterceptor(apiKeyStore: TMDBApiKeyStore) =
    TMDBRequestInterceptor(apiKeyStore.getApiKey(), 86400)

private fun createCache(context: Context) = Cache(context.cacheDir, 10 * 1024 * 1024)

private fun createHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
  val builder = OkHttpClient().newBuilder()

  if (BuildConfig.DEBUG) {
    builder.addInterceptor(HttpLoggingInterceptor(Logger.DEFAULT)
        .setLevel(HttpLoggingInterceptor.Level.BODY))
  }
  return builder
      .cache(cache)
      .addInterceptor(interceptor)
      .build()
}