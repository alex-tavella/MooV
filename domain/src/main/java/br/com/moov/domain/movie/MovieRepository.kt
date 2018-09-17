package br.com.moov.domain.movie

interface MovieRepository {
  suspend fun getMovies(page: Int): List<Movie>
}