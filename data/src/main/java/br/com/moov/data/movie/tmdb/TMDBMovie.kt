package br.com.moov.data.movie.tmdb

data class TMDBMovie(
    val id: Int,
    val poster_path: String,
    val adult: Boolean,
    val overview: String,
    val release_date: String,
    val genre_ids: List<String>,
    val original_title: String,
    val original_language: String,
    val title: String,
    val backdrop_path: String,
    val popularity: Float,
    val vote_count: Int,
    val video: Boolean,
    val vote_average: Float)