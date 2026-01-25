package com.siddhika.ui.screens.scripture

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.domain.model.Bookmark
import com.siddhika.domain.model.Scripture
import com.siddhika.domain.usecase.scripture.GetScripturesUseCase
import com.siddhika.domain.usecase.scripture.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScriptureViewModel(
    getScripturesUseCase: GetScripturesUseCase
) : ScreenModel {

    val scriptures: StateFlow<List<Scripture>> = getScripturesUseCase()
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}

class ScriptureReaderViewModel(
    private val scriptureId: Long,
    private val getScripturesUseCase: GetScripturesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ScreenModel {

    private val _scripture = MutableStateFlow<Scripture?>(null)
    val scripture: StateFlow<Scripture?> = _scripture.asStateFlow()

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    init {
        loadScripture()
    }

    private fun loadScripture() {
        screenModelScope.launch {
            _scripture.value = getScripturesUseCase.byId(scriptureId)
        }
    }

    fun toggleBookmark() {
        screenModelScope.launch {
            toggleBookmarkUseCase(
                scriptureId = scriptureId,
                chapterNumber = 1 // Default to chapter 1
            )
            _isBookmarked.value = !_isBookmarked.value
        }
    }
}

class BookmarksViewModel(
    toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ScreenModel {

    val bookmarks: StateFlow<List<Bookmark>> = toggleBookmarkUseCase.getAll()
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val deleteUseCase = toggleBookmarkUseCase

    fun deleteBookmark(id: Long) {
        screenModelScope.launch {
            deleteUseCase.delete(id)
        }
    }
}
