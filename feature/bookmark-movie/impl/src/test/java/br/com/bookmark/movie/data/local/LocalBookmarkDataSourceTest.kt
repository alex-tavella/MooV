package br.com.bookmark.movie.data.local

import br.com.bookmark.movie.data.local.entity.MovieBookmark
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class LocalBookmarkDataSourceTest {

    @Test
    fun bookmarkMovie_insertsOnDao() = runBlocking {
        val movieBookmarksDao = TestMovieBookmarksDao()
        val dataSource = LocalBookmarkDataSource(movieBookmarksDao)

        dataSource.bookmarkMovie(movieId = 123)

        assertEquals(listOf(MovieBookmark(123)), movieBookmarksDao.getAll())
    }

    @Test
    fun unbookmarkMovie_removesFromDao() = runBlocking {
        val movieBookmarksDao = TestMovieBookmarksDao(listOf(MovieBookmark(123)))
        val dataSource = LocalBookmarkDataSource(movieBookmarksDao)

        dataSource.unBookmarkMovie(123)

        assertEquals(emptyList<MovieBookmark>(), movieBookmarksDao.getAll())
    }
}
