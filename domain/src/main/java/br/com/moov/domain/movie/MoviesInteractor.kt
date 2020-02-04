package br.com.moov.domain.movie

import javax.inject.Inject

interface MoviesInteractor {
    suspend fun getPopularMovies(page: Int = 1): List<Movie>
}

class MoviesInteractorImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : MoviesInteractor {
    override suspend fun getPopularMovies(page: Int): List<Movie> =
        movieRepository.getPopularMovies(
            page
        )
}
