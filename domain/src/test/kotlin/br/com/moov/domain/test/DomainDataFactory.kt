package br.com.moov.domain.test

import br.com.moov.domain.movie.Movie
import br.com.moov.domain.movie.MovieDetail
import br.com.moov.test.DataFactory

object DomainDataFactory {
  fun newMovie() =
      Movie(
          DataFactory.randomInt(),
          DataFactory.randomString(),
          DataFactory.randomPathString())

  fun newMovieList(count: Int) =
      mutableListOf<Movie>().apply {
        repeat(count) {
          add(newMovie())
        }
      }

  fun newMovieDetail() =
      MovieDetail(
          id = DataFactory.randomInt(),
          title = DataFactory.randomString(),
          genres = DataFactory.randomStringList(DataFactory.randomInt(5)),
          backdropUrl = DataFactory.randomPathString(),
          posterUrl = DataFactory.randomPathString(),
          voteAverage = DataFactory.randomScore(),
          overview = DataFactory.randomString(),
          releaseDate = DataFactory.randomDateAsString(),
          originalLanguage = DataFactory.randomString(),
          popularity = DataFactory.randomScore()
      )
}