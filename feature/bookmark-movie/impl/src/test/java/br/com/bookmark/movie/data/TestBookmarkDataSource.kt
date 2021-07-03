package br.com.bookmark.movie.data

class TestBookmarkDataSource(
    initialBookmarks: List<Int> = emptyList()
) : BookmarkDataSource {

    private val bookmarks: MutableList<Int> = initialBookmarks.toMutableList()

    override suspend fun bookmarkMovie(movieId: Int) {
        bookmarks.add(movieId)
    }

    override suspend fun unBookmarkMovie(movieId: Int) {
        bookmarks.removeIf { it == movieId }
    }

    fun getBookmarks(): List<Int> = bookmarks.toList()
}
