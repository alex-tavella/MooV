package br.com.bookmark.movie.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultBookmarkRepositoryTest {

    private val bookmarkDataSource = TestBookmarkDataSource(listOf(123, 789))
    private val bookmarkRepository = DefaultBookmarkRepository(bookmarkDataSource)

    @Test
    fun bookmarkMovie_insertsOnDataSource() = runBlocking {
        bookmarkRepository.bookmarkMovie(456)

        assertEquals(listOf(123, 789, 456), bookmarkDataSource.getBookmarks())
    }

    @Test
    fun unBookmarkMovie_removesFromDataSource() = runBlocking {
        bookmarkRepository.unBookmarkMovie(123)

        assertEquals(listOf(789), bookmarkDataSource.getBookmarks())
    }
}
