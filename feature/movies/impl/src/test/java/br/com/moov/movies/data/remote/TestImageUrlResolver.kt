package br.com.moov.movies.data.remote

import br.com.moov.core.ImageUrlResolver

class TestImageUrlResolver(
    private val baseUrl: String = "https://cdn.tmdb.com/images"
) : ImageUrlResolver {
    override suspend fun getPosterUrl(posterPath: String): String {
        return "$baseUrl/poster/$posterPath"
    }

    override suspend fun getBackdropUrl(backdropPath: String): String {
        return "$baseUrl/backdrop/$backdropPath"
    }
}
