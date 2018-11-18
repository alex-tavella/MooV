package br.com.moov.domain.movie

data class Movie(
    val id: Int?,
    val title: String?,
    val thumbnailUrl: String?,
    val isBookmarked: Boolean = false)