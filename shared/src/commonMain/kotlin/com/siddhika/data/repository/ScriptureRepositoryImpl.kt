package com.siddhika.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.siddhika.core.util.DateTimeUtil
import com.siddhika.data.local.database.SiddhikaDatabase
import com.siddhika.data.mapper.toDomain
import com.siddhika.data.remote.api.ScriptureApiService
import com.siddhika.data.remote.mapper.toDomain
import com.siddhika.domain.model.Bookmark
import com.siddhika.domain.model.Scripture
import com.siddhika.domain.repository.ScriptureRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ScriptureRepositoryImpl(
    private val database: SiddhikaDatabase,
    private val apiService: ScriptureApiService
) : ScriptureRepository {

    private val queries = database.siddhikaDatabaseQueries

    override fun getAllScriptures(): Flow<List<Scripture>> = flow {
        try {
            val remote = apiService.getAll().map { it.toDomain() }
            emit(remote)
        } catch (_: Exception) {
            emitAll(
                queries.getAllScriptures()
                    .asFlow()
                    .mapToList(Dispatchers.IO)
                    .map { entities -> entities.map { it.toDomain() } }
            )
        }
    }

    override fun getScripturesByCategory(category: String): Flow<List<Scripture>> = flow {
        try {
            val remote = apiService.getByCategory(category).map { it.toDomain() }
            emit(remote)
        } catch (_: Exception) {
            emitAll(
                queries.getScripturesByCategory(category)
                    .asFlow()
                    .mapToList(Dispatchers.IO)
                    .map { entities -> entities.map { it.toDomain() } }
            )
        }
    }

    override suspend fun getScriptureById(id: Long): Scripture? = withContext(Dispatchers.IO) {
        try {
            apiService.getById(id).toDomain()
        } catch (_: Exception) {
            queries.getScriptureById(id).executeAsOneOrNull()?.toDomain()
        }
    }

    override suspend fun addScripture(scripture: Scripture) = withContext(Dispatchers.IO) {
        queries.insertScripture(
            title = scripture.title,
            description = scripture.description,
            content = scripture.content,
            category = scripture.category,
            language = scripture.language,
            totalChapters = scripture.totalChapters.toLong()
        )
    }

    override fun getAllBookmarks(): Flow<List<Bookmark>> =
        queries.getAllBookmarks()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override fun getBookmarksByScripture(scriptureId: Long): Flow<List<Bookmark>> =
        queries.getBookmarksByScripture(scriptureId)
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> entities.map { it.toDomain() } }

    override suspend fun getBookmarkById(id: Long): Bookmark? = withContext(Dispatchers.IO) {
        queries.getBookmarkById(id).executeAsOneOrNull()?.toDomain()
    }

    override suspend fun addBookmark(bookmark: Bookmark) = withContext(Dispatchers.IO) {
        queries.insertBookmark(
            scriptureId = bookmark.scriptureId,
            chapterNumber = bookmark.chapterNumber.toLong(),
            verseNumber = bookmark.verseNumber?.toLong(),
            note = bookmark.note,
            createdAt = DateTimeUtil.nowMillis()
        )
    }

    override suspend fun updateBookmarkNote(id: Long, note: String?) = withContext(Dispatchers.IO) {
        queries.updateBookmarkNote(note, id)
    }

    override suspend fun deleteBookmark(id: Long) = withContext(Dispatchers.IO) {
        queries.deleteBookmark(id)
    }

    override suspend fun isVerseBookmarked(scriptureId: Long, chapter: Int, verse: Int?): Boolean =
        withContext(Dispatchers.IO) {
            val count = queries.isVerseBookmarked(
                scriptureId,
                chapter.toLong(),
                verse?.toLong()
            ).executeAsOne()
            count > 0
        }
}
