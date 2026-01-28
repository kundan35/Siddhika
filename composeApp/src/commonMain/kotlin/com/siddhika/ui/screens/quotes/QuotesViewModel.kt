package com.siddhika.ui.screens.quotes

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.core.util.UiState
import com.siddhika.domain.model.Quote
import com.siddhika.domain.usecase.quote.GetAllQuotesUseCase
import com.siddhika.domain.usecase.quote.ToggleQuoteFavoriteUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuotesViewModel(
    private val getAllQuotesUseCase: GetAllQuotesUseCase,
    private val toggleQuoteFavoriteUseCase: ToggleQuoteFavoriteUseCase
) : ScreenModel {

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _showFavorites = MutableStateFlow(false)
    val showFavorites: StateFlow<Boolean> = _showFavorites.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val quotes: StateFlow<UiState<List<Quote>>> = combine(
        _selectedCategory,
        _showFavorites
    ) { category, favorites ->
        Pair(category, favorites)
    }.flatMapLatest { (category, favorites) ->
        when {
            favorites -> getAllQuotesUseCase.favorites()
            category != null -> getAllQuotesUseCase.byCategory(category)
            else -> getAllQuotesUseCase()
        }.map { list ->
            if (list.isEmpty()) UiState.Empty else UiState.Success(list)
        }.catch { emit(UiState.Error(it.message ?: "Failed to load quotes")) }
    }.stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.Loading
    )

    fun setCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun toggleShowFavorites() {
        _showFavorites.value = !_showFavorites.value
    }

    fun toggleFavorite(quote: Quote) {
        screenModelScope.launch {
            toggleQuoteFavoriteUseCase(quote.id, !quote.isFavorite)
        }
    }

    fun retry() {
        // Re-trigger by resetting filters to force resubscription
        val cat = _selectedCategory.value
        val fav = _showFavorites.value
        _selectedCategory.value = null
        _showFavorites.value = false
        _selectedCategory.value = cat
        _showFavorites.value = fav
    }
}
