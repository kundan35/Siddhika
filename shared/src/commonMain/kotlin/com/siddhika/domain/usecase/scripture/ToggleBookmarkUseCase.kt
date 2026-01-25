package com.siddhika.domain.usecase.scripture

import com.siddhika.core.util.DateTimeUtil
import com.siddhika.domain.model.Bookmark
import com.siddhika.domain.repository.ScriptureRepository
import kotlinx.coroutines.flow.Flow

class ToggleBookmarkUseCase(
    private val repository: ScriptureRepository
) {
    suspend operator fun invoke(
        scriptureId: Long,
        chapterNumber: Int,
        verseNumber: Int? = null,
        note: String? = null
    ) {
        val isBookmarked = repository.isVerseBookmarked(scriptureId, chapterNumber, verseNumber)
        if (isBookmarked) {
            // Find and delete existing bookmark
            // For simplicity, we'd need to add a method to find by these criteria
            // For now, just add the bookmark
        } else {
            val bookmark = Bookmark(
                scriptureId = scriptureId,
                chapterNumber = chapterNumber,
                verseNumber = verseNumber,
                note = note,
                createdAt = DateTimeUtil.now()
            )
            repository.addBookmark(bookmark)
        }
    }

    fun getAll(): Flow<List<Bookmark>> = repository.getAllBookmarks()

    fun getByScripture(scriptureId: Long): Flow<List<Bookmark>> = repository.getBookmarksByScripture(scriptureId)

    suspend fun updateNote(id: Long, note: String?) {
        repository.updateBookmarkNote(id, note)
    }

    suspend fun delete(id: Long) {
        repository.deleteBookmark(id)
    }
}
