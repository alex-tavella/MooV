package br.com.moov.moviedetails.domain 

class TestMovieDetailRepository(
    private val moviesDetails: List<MovieDetail> = emptyList()
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): MovieDetail? {
        return moviesDetails.find { it.id == movieId }
    }
}
