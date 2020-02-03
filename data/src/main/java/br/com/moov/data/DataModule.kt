package br.com.moov.data

import android.content.Context
import br.com.moov.BuildConfig
import br.com.moov.data.movie.MovieDataSource
import br.com.moov.data.movie.MovieRepositoryImpl
import br.com.moov.data.movie.TMDBMovieDataSource
import br.com.moov.data.movie.bookmark.BookmarkDataSource
import br.com.moov.data.movie.bookmark.LocalDataSource
import br.com.moov.data.movie.bookmark.database.MooVDatabase
import br.com.moov.data.movie.tmdb.TMDBApiKeyStore
import br.com.moov.data.movie.tmdb.TMDBDApi
import br.com.moov.data.movie.tmdb.TMDBRequestInterceptor
import br.com.moov.domain.movie.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
private annotation class BaseUrl

@Module(includes = [RemoteDataModule::class, LocalDataModule::class])
interface DataModule {
  @[Binds Singleton]
  fun bindsMovieDataSource(tmdbMovieDataSource: TMDBMovieDataSource): MovieDataSource

  @[Binds Singleton]
  fun bindsBookmarkDataSource(localDataSource: LocalDataSource): BookmarkDataSource

  @[Binds Singleton]
  fun bindsMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository

  @[Binds Singleton]
  fun bindsTmdbInterceptor(tmdbRequestInterceptor: TMDBRequestInterceptor): Interceptor
}

@Module
object RemoteDataModule {

  @[Provides JvmStatic Singleton BaseUrl]
  fun providesBaseUrl() = "https://api.themoviedb.org"

  @[Provides JvmStatic Singleton]
  fun providesApiKey(apiKeyStore: TMDBApiKeyStore): String = apiKeyStore.getApiKey()

  @[Provides JvmStatic Singleton]
  fun providesCacheDuration(): Long = TimeUnit.DAYS.toSeconds(1)

  @[Provides JvmStatic Singleton]
  fun providesCache(context: Context): Cache = Cache(context.cacheDir, 10 * 1024 * 1024)

  @[Provides JvmStatic Singleton]
  fun providesOkHttpClient(cache: Cache, interceptor: Interceptor): OkHttpClient {
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

  @[Provides JvmStatic Singleton]
  fun providesRetrofit(okHttpClient: OkHttpClient, @BaseUrl baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
  }

  @[Provides JvmStatic Singleton]
  fun providesTMDBApi(retrofit: Retrofit): TMDBDApi {
    return retrofit.create(TMDBDApi::class.java)
  }
}

@Module
object LocalDataModule {
  @[Provides JvmStatic Singleton]
  fun providesMooVDatabase(context: Context): MooVDatabase = MooVDatabase.create(context)
}
