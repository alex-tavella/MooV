package br.com.moov.data.movie

import br.com.moov.data.movie.bookmark.BookmarkDataSource
import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieDetail
import br.com.moov.domain.movie.MovieRepository

class MovieRepositoryImpl(
    private val tmdbMovieDataSource: MovieDataSource,
    private val bookmarkDataSource: BookmarkDataSource,
    private val movieMapper: MovieMapper) : MovieRepository {

  override suspend fun getPopularMovies(page: Int): List<Movie> {
    val tmdbMovies = tmdbMovieDataSource.getMovies(page, SORT_ORDER_POPULARITY,
        THRESHOLD_VOTE_COUNT)
    val imageConfigs = tmdbMovieDataSource.getImageConfigs()

    val bookmarkedIds = bookmarkDataSource
        .getBookmarks(tmdbMovies.mapNotNull { it.id }.toIntArray())
        .map { it.id }

    return tmdbMovies
        .map {
          movieMapper.map(it, imageConfigs).copy(isBookmarked = bookmarkedIds.contains(it.id))
        }
  }

  override suspend fun getMovieDetail(movieId: Int): MovieDetail {
    val tmdbMovie = tmdbMovieDataSource.getMovieDetail(movieId)
    val imageConfigs = tmdbMovieDataSource.getImageConfigs()
    val movieBookmarked = bookmarkDataSource.isMovieBookmarked(movieId)
    return tmdbMovie.let { movieMapper.map(it, imageConfigs).copy(isBookmarked = movieBookmarked) }
  }

  override suspend fun getBookmarkedMovies(movies: List<Movie>): List<Movie> {
    val bookmarkedIds = bookmarkDataSource
        .getBookmarks(movies.mapNotNull { it.id }.toIntArray())
        .map { it.id }

    return movies.map { it.copy(isBookmarked = bookmarkedIds.contains(it.id)) }
  }

  override suspend fun bookmark(movie: Movie) {
    movieMapper.toEntity(movie)?.let { bookmarkDataSource.bookmarkMovie(it) }
  }

  override suspend fun bookmark(movieDetail: MovieDetail) {
    movieMapper.toEntity(movieDetail)?.let { bookmarkDataSource.bookmarkMovie(it) }
  }

  override suspend fun unbookmark(movieId: Int) {
    bookmarkDataSource.unbookmarkMovie(movieId)
  }

  companion object {
    const val SORT_ORDER_POPULARITY = "popularity.desc"
    const val THRESHOLD_VOTE_COUNT = 100
  }
}