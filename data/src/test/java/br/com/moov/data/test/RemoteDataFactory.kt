package br.com.moov.data.test

import br.com.moov.data.movie.tmdb.ConfigurationsResponse
import br.com.moov.data.movie.tmdb.Genre
import br.com.moov.data.movie.tmdb.ImageConfigurations
import br.com.moov.data.movie.tmdb.MovieDiscoverResponse
import br.com.moov.data.movie.tmdb.TMDBMovie
import br.com.moov.data.movie.tmdb.TMDBMovieDetail
import br.com.moov.test.DataFactory

object RemoteDataFactory {
    fun newMovieResponse() = MovieDiscoverResponse(
        DataFactory.randomInt(),
        newTMDBMoviesList(20),
        DataFactory.randomInt(),
        DataFactory.randomInt()
    )

    fun newTMDBMovie() = TMDBMovie(
        id = DataFactory.randomInt(),
        poster_path = DataFactory.randomPathString(),
        original_title = DataFactory.randomString(),
        popularity = DataFactory.randomScore(),
        vote_count = DataFactory.randomInt()
    )

    fun newTMDBMovieDetail() = TMDBMovieDetail(
        id = DataFactory.randomInt(),
        poster_path = DataFactory.randomPathString(),
        original_title = DataFactory.randomString(),
        title = DataFactory.randomString(),
        adult = DataFactory.randomBoolean(),
        vote_average = DataFactory.randomScore(),
        backdrop_path = DataFactory.randomPathString(),
        original_language = DataFactory.randomString(),
        overview = DataFactory.randomString(),
        popularity = DataFactory.randomScore(),
        release_date = DataFactory.randomDateAsString(),
        video = DataFactory.randomBoolean(),
        vote_count = DataFactory.randomInt(),
        genres = newGenreList()
    )

    fun newGenreList() = mutableListOf<Genre>().apply {
        repeat(DataFactory.randomInt(10)) { add(newGenre()) }
    }

    fun newGenre() =
        Genre(DataFactory.randomInt(), DataFactory.randomString())

    fun newTMDBMoviesList(count: Int) = mutableListOf<TMDBMovie>().apply {
        repeat(count) { add(newTMDBMovie()) }
    }

    fun newConfigurationsResponse() = ConfigurationsResponse(newImageConfigurations())

    fun newImageConfigurations() =
        ImageConfigurations(
            base_url = DataFactory.randomUrl(),
            poster_sizes = newImageConfigOptionList(DataFactory.randomInt()),
            backdrop_sizes = newImageConfigOptionList(DataFactory.randomInt())
        )

    private fun newImageConfigOptionList(count: Int): List<String> {
        return mutableListOf<String>().apply {
            repeat(count) { add(newImageConfigString()) }
        }
    }

    private fun newImageConfigString() = "w${DataFactory.randomInt()}"
}
