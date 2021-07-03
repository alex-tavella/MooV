package br.com.bookmark.movie.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UnBookmarkMovieTest {
    private val repository = TestBookmarkRepository(listOf(123, 456))

    private val unbookmark = UnBookmarkMovie(repository)

    @Test
    fun invoke_addOnRepository() = runBlocking {
        unbookmark(456)

        assertEquals(listOf(123), repository.getBookmarks())
    }
}
