/*
 * Copyright 2020 Alex Almeida Tavella
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        val imageConfigs =
            RemoteDataFactory.newImageConfigurations().copy(poster_sizes = emptyList())

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
