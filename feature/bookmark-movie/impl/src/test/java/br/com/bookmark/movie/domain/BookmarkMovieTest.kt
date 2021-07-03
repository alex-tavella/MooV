package br.com.bookmark.movie.domain

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class BookmarkMovieTest {

    private val repository = TestBookmarkRepository(listOf(123, 456))

    private val bookmark = BookmarkMovie(repository)

    @Test
    fun invoke_addOnRepository() = runBlocking {
        bookmark(789)

        assertEquals(listOf(123, 456, 789), repository.getBookmarks())
    }
}
