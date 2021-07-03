package br.com.moov.moviedetails.data

import br.com.moov.moviedetails.domain.MovieDetail

class TestMovieDetailDataSource(
    private val moviesDetails: List<MovieDetail> = emptyList()
) : MovieDetailDataSource {
    override suspend fun getMovieDetail(movieId: Int): MovieDetail? {
        return moviesDetails.find { it.id == movieId }
    }
}
