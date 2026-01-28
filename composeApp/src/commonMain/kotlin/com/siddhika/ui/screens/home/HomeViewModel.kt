package com.siddhika.ui.screens.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.siddhika.core.util.UiState
import com.siddhika.domain.model.Quote
import com.siddhika.domain.usecase.quote.GetDailyQuoteUseCase
import com.siddhika.domain.usecase.quote.ToggleQuoteFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getDailyQuoteUseCase: GetDailyQuoteUseCase,
    private val toggleQuoteFavoriteUseCase: ToggleQuoteFavoriteUseCase
) : ScreenModel {

    val dailyQuote: StateFlow<UiState<Quote>> = getDailyQuoteUseCase()
        .map { quote ->
            if (quote != null) UiState.Success(quote) else UiState.Empty
        }
        .catch { emit(UiState.Error(it.message ?: "Failed to load daily quote")) }
        .stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )

    fun toggleFavorite(quote: Quote) {
        screenModelScope.launch {
            toggleQuoteFavoriteUseCase(quote.id, !quote.isFavorite)
        }
    }
}
