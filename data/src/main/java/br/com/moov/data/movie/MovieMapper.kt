package br.com.moov.data.movie

import br.com.moov.data.movie.bookmark.database.entity.MovieEntity
import br.com.moov.data.movie.tmdb.ImageConfigurations
import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.data.movie.tmdb.TMDBMovieDetail
import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieDetail
import javax.inject.Inject

open class MovieMapper @Inject constructor() {
  open fun map(tmdbMovie: TMDBMovie, imageConfigs: ImageConfigurations): Movie {
    return Movie(tmdbMovie.id, tmdbMovie.original_title,
        getPosterUrl(imageConfigs, tmdbMovie.poster_path))
  }

  open fun map(tmdbMovie: TMDBMovieDetail, imageConfigs: ImageConfigurations): MovieDetail {
    return MovieDetail(
        id = tmdbMovie.id,
        title = tmdbMovie.original_title,
        posterUrl = getPosterUrl(imageConfigs, tmdbMovie.poster_path),
        popularity = tmdbMovie.popularity,
        originalLanguage = tmdbMovie.original_language,
        releaseDate = tmdbMovie.release_date,
        overview = tmdbMovie.overview,
        voteAverage = tmdbMovie.vote_average,
        backdropUrl = getBackdropUrl(imageConfigs, tmdbMovie.backdrop_path),
        genres = tmdbMovie.genres.mapNotNull { it.name })
  }

  private fun getPosterUrl(imageConfigs: ImageConfigurations, path: String?): String? {
    if (!imageConfigs.base_url.isNullOrBlank() &&
        !path.isNullOrBlank() &&
        imageConfigs.poster_sizes.isNotEmpty()) {
      return imageConfigs.base_url + selectConfigurationOption(imageConfigs.poster_sizes) + path
    }
    return null
  }

  private fun getBackdropUrl(imageConfigs: ImageConfigurations, path: String?): String? {
    if (!imageConfigs.base_url.isNullOrBlank() &&
        !path.isNullOrBlank() &&
        imageConfigs.backdrop_sizes.isNotEmpty()) {
      return imageConfigs.base_url + selectConfigurationOption(imageConfigs.backdrop_sizes) + path
    }
    return null
  }

  private fun selectConfigurationOption(options: List<String>): String {
    return options[(options.size / 2)]
  }

  fun toEntity(movie: Movie): MovieEntity? {
    return movie.id?.let { MovieEntity(it, movie.title, movie.thumbnailUrl) }
  }

  fun toEntity(movie: MovieDetail): MovieEntity? {
    return movie.id?.let { MovieEntity(it, movie.title, movie.posterUrl) }
  }
}
