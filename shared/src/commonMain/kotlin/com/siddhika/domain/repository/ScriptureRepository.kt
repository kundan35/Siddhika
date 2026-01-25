package com.siddhika.domain.repository

import com.siddhika.domain.model.Bookmark
import com.siddhika.domain.model.Scripture
import kotlinx.coroutines.flow.Flow

interface ScriptureRepository {
    fun getAllScriptures(): Flow<List<Scripture>>
    fun getScripturesByCategory(category: String): Flow<List<Scripture>>
    suspend fun getScriptureById(id: Long): Scripture?
    suspend fun addScripture(scripture: Scripture)

    fun getAllBookmarks(): Flow<List<Bookmark>>
    fun getBookmarksByScripture(scriptureId: Long): Flow<List<Bookmark>>
    suspend fun getBookmarkById(id: Long): Bookmark?
    suspend fun addBookmark(bookmark: Bookmark)
    suspend fun updateBookmarkNote(id: Long, note: String?)
    suspend fun deleteBookmark(id: Long)
    suspend fun isVerseBookmarked(scriptureId: Long, chapter: Int, verse: Int?): Boolean
}
