package br.com.moov.domain.movie

data class MovieDetail(
    val id: Int?,
    val title: String?,
    val posterUrl: String? = null,
    val overview: String? = null,
    val releaseDate: String? = null,
    val genres: List<String> = emptyList(),
    val originalLanguage: String? = null,
    val backdropUrl: String? = null,
    val popularity: Float = 0F,
    val voteAverage: Float = 0F,
    val isBookmarked: Boolean = false)