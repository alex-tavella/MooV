package br.com.moov.data.test

import br.com.moov.data.movie.bookmark.database.entity.MovieEntity
import br.com.moov.test.DataFactory

object LocalDataFactory {

  fun newMovieList() = mutableListOf<MovieEntity>().apply {
    repeat(DataFactory.randomInt(10)) { add(newMovie()) }
  }

  fun newMovie() = MovieEntity(
      id = DataFactory.randomInt(),
      title = DataFactory.randomString(),
      thumbnailPath = DataFactory.randomPathString())
}