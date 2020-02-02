package br.com.moov.domain.movie

interface MovieRepository {
  suspend fun getPopularMovies(page: Int): List<Movie>
  suspend fun getMovieDetail(movieId: Int): MovieDetail
  suspend fun getBookmarkedMovies(movies: List<Movie>): List<Movie>
  suspend fun bookmark(movie: Movie)
  suspend fun bookmark(movieDetail: MovieDetail)
  suspend fun unbookmark(movieId: Int)
}