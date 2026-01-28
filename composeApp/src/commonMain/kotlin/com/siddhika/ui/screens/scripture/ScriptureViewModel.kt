package com.siddhika.ui.screens.scripture

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.core.util.UiState
import com.siddhika.domain.model.Bookmark
import com.siddhika.domain.model.Scripture
import com.siddhika.domain.usecase.scripture.GetScripturesUseCase
import com.siddhika.domain.usecase.scripture.ToggleBookmarkUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScriptureViewModel(
    private val getScripturesUseCase: GetScripturesUseCase
) : ScreenModel {

    val scriptures: StateFlow<UiState<List<Scripture>>> = getScripturesUseCase()
        .map { list ->
            if (list.isEmpty()) UiState.Empty else UiState.Success(list)
        }
        .catch { emit(UiState.Error(it.message ?: "Failed to load scriptures")) }
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun retry() {
        // Flow from use case is reactive; no explicit retry needed for Room/SQLDelight flows.
    }
}

class ScriptureReaderViewModel(
    private val scriptureId: Long,
    private val getScripturesUseCase: GetScripturesUseCase,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : ScreenModel {

    private val _scripture = MutableStateFlow<UiState<Scripture>>(UiState.Loading)
    val scripture: StateFlow<UiState<Scripture>> = _scripture.asStateFlow()

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked.asStateFlow()

    init {
        loadScripture()
    }

    private fun loadScripture() {
        _scripture.value = UiState.Loading
        screenModelScope.launch {
            try {
                val s = getScripturesUseCase.byId(scriptureId)
                if (s != null) {
                    _scripture.value = UiState.Success(s)
                } else {
                    _scripture.value = UiState.Empty
                }
            } catch (e: Exception) {
                _scripture.value = UiState.Error(e.message ?: "Failed to load scripture")
            }
        }
    }

    fun retry() {
        loadScripture()
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
