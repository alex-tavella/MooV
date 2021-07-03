package br.com.moov.movies.data.remote

import java.io.IOException

class TestTmdbMoviesApi(
    private val movies: List<TmdbMovie> = emptyList(),
    private val pageSize: Int = 3,
) : TmdbMoviesApi {

    override suspend fun discoverMovies(
        page: Int,
        sortBy: String,
        voteCount: Int
    ): MovieDiscoverResponse {
        return if (movies.isNotEmpty()) {
            val filterResult = movies
                .filter { it.voteCount >= voteCount }
                .sortedByDescending {
                    if (sortBy == "popularity.desc") it.popularity.toInt()
                    else it.voteCount
                }
            MovieDiscoverResponse(
                page = page,
                results = filterResult.take(pageSize),
                totalResults = filterResult.size,
                totalPages = filterResult.size / pageSize
            )
        } else {
            throw IOException("404: Not found")
        }
    }
}
