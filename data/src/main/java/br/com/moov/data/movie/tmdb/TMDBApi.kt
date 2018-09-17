package br.com.moov.data.movie.tmdb

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBDApi {

  @GET("/3/discover/movie")
  fun discoverMovies(@Query("page") page: Int, @Query("sort_by") sortBy: String,
      @Query("vote_count.gte") voteCount: Int): Call<MovieDiscoverResponse>

  @GET("/3/configuration")
  fun getConfiguration(): Call<ConfigurationsResponse>

  @GET("/3/movie/{movieId}")
  fun getMovie(@Path("movieId") movieId: Int): Call<TMDBMovie>

  companion object {
    private const val API_BASE_URL = "https://api.themoviedb.org"

    fun create(okHttpClient: OkHttpClient): TMDBDApi {
      return Retrofit.Builder()
          .baseUrl(API_BASE_URL)
          .client(okHttpClient)
          .addConverterFactory(GsonConverterFactory.create())
          .build()
          .create(TMDBDApi::class.java)
    }
  }
}

data class ConfigurationsResponse(
    val images: ImageConfigurations,
    val change_keys: List<String>)

data class ImageConfigurations(
    val base_url: String,
    val secure_base_url: String,
    val backdrop_sizes: List<String>,
    val logo_sizes: List<String>,
    val poster_sizes: List<String>,
    val profile_sizes: List<String>,
    val still_sizes: List<String>)

data class MovieDiscoverResponse(
    val page: Int,
    val results: List<TMDBMovie>,
    val total_results: Int,
    val total_pages: Int)