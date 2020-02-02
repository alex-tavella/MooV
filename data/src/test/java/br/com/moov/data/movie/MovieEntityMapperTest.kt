package br.com.moov.data.movie

import br.com.moov.data.test.RemoteDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieEntityMapperTest {

  private val mapper = MovieMapper()

  @Test
  fun `map regular response`() {
    // Given
    val movie = RemoteDataFactory.newTMDBMovie()
    val imageConfigs = RemoteDataFactory.newImageConfigurations()

    // When
    val result = mapper.map(movie, imageConfigs)

    // Then
    assert(result.id == movie.id)
    assert(result.title == movie.original_title)
    val imageConfig = imageConfigs.poster_sizes[imageConfigs.poster_sizes.size / 2]
    assert(result.thumbnailUrl == imageConfigs.base_url + imageConfig + movie.poster_path)
  }

  @Test
  fun `map posterless`() {
    // Given
    val movie = RemoteDataFactory.newTMDBMovie().copy(poster_path = null)
    val imageConfigs = RemoteDataFactory.newImageConfigurations()

    // When
    val result = mapper.map(movie, imageConfigs)

    // Then
    assert(result.id == movie.id)
    assert(result.title == movie.original_title)
    assert(result.thumbnailUrl == null)
  }

  @Test
  fun `map no config options`() {
    // Given
    val movie = RemoteDataFactory.newTMDBMovie()
    val imageConfigs = RemoteDataFactory.newImageConfigurations().copy(poster_sizes = emptyList())

    // When
    val result = mapper.map(movie, imageConfigs)

    // Then
    assert(result.id == movie.id)
    assert(result.title == movie.original_title)
    assert(result.thumbnailUrl == null)
  }

  @Test
  fun `map no base url`() {
    // Given
    val movie = RemoteDataFactory.newTMDBMovie()
    val imageConfigs = RemoteDataFactory.newImageConfigurations().copy(base_url = null)

    // When
    val result = mapper.map(movie, imageConfigs)

    // Then
    assert(result.id == movie.id)
    assert(result.title == movie.original_title)
    assert(result.thumbnailUrl == null)
  }

  @Test
  fun `map no id`() {
    // Given
    val movie = RemoteDataFactory.newTMDBMovie().copy(id = null)
    val imageConfigs = RemoteDataFactory.newImageConfigurations()

    // When
    val result = mapper.map(movie, imageConfigs)

    // Then
    assert(result.id == movie.id)
    assert(result.id == null)
    assert(result.title == movie.original_title)
    val imageConfig = imageConfigs.poster_sizes[imageConfigs.poster_sizes.size / 2]
    assert(result.thumbnailUrl == imageConfigs.base_url + imageConfig + movie.poster_path)
  }

  @Test
  fun `map no title`() {
    // Given
    val movie = RemoteDataFactory.newTMDBMovie().copy(original_title = null)
    val imageConfigs = RemoteDataFactory.newImageConfigurations()

    // When
    val result = mapper.map(movie, imageConfigs)

    // Then
    assert(result.id == movie.id)
    assert(result.title == movie.original_title)
    assert(result.title == null)
    val imageConfig = imageConfigs.poster_sizes[imageConfigs.poster_sizes.size / 2]
    assert(result.thumbnailUrl == imageConfigs.base_url + imageConfig + movie.poster_path)
  }

  @Test
  fun `map detailed`() {
    // Given
    val movie = RemoteDataFactory.newTMDBMovieDetail()
    val imageConfigs = RemoteDataFactory.newImageConfigurations()

    // When
    val result = mapper.map(movie, imageConfigs)

    // Then
    assert(result.id == movie.id)
    assert(result.title == movie.original_title)
    val posterConfig = imageConfigs.poster_sizes[imageConfigs.poster_sizes.size / 2]
    assert(result.posterUrl == imageConfigs.base_url + posterConfig + movie.poster_path)
    val backdropConfig = imageConfigs.backdrop_sizes[imageConfigs.backdrop_sizes.size / 2]
    assert(result.backdropUrl == imageConfigs.base_url + backdropConfig + movie.backdrop_path)
    assert(result.genres == movie.genres.mapNotNull { it.name })
    assert(result.originalLanguage == movie.original_language)
    assert(result.overview == movie.overview)
    assert(result.popularity == movie.popularity)
    assert(result.releaseDate == movie.release_date)
    assert(result.voteAverage == movie.vote_average)
  }
}