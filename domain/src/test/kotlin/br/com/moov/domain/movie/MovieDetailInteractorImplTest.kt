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
package br.com.moov.domain.movie

import br.com.moov.domain.test.DomainDataFactory
import br.com.moov.test.DataFactory
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDetailInteractorImplTest {

    private val repository: MovieRepository = mock()

    private lateinit var interactor: MovieDetailInteractor

    @Before
    fun setup() {
        interactor = MovieDetailInteractorImpl(repository)
    }

    @Test
    fun `get movie details`() {
        runBlocking {
            // Given
            val expected = DomainDataFactory.newMovieDetail()
            whenever(repository.getMovieDetail(any()))
                .thenReturn(expected)

            // When
            val movieId = DataFactory.randomInt()
            val result = interactor.getMovieDetail(movieId)

            // Then
            assert(result == expected)
            val idCaptor = argumentCaptor<Int>()
            verify(repository).getMovieDetail(idCaptor.capture())
            assert(idCaptor.firstValue == movieId)
        }
    }

    @Test
    fun `get movie details error`() {
        runBlocking {
            // Given
            whenever(repository.getMovieDetail(any())).thenThrow(IllegalStateException())

            // When
            val movieId = DataFactory.randomInt()
            val result = runCatching { interactor.getMovieDetail(movieId) }

            // Then
            assert(result.isFailure)
            assert(!result.isSuccess)
            assert(result.getOrNull() == null)
            assert(result.exceptionOrNull() is IllegalStateException)
            val idCaptor = argumentCaptor<Int>()
            verify(repository).getMovieDetail(idCaptor.capture())
            assert(idCaptor.firstValue == movieId)
        }
    }
}
