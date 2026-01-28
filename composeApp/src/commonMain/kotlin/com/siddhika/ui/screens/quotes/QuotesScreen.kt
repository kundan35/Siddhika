package com.siddhika.ui.screens.quotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.siddhika.core.util.Constants
import com.siddhika.core.util.UiState
import com.siddhika.ui.components.EmptyContent
import com.siddhika.ui.components.ErrorContent
import com.siddhika.ui.components.LoadingContent
import com.siddhika.ui.components.QuoteCard

class QuotesListScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<QuotesViewModel>()
        val state by viewModel.quotes.collectAsState()
        val selectedCategory by viewModel.selectedCategory.collectAsState()
        val showFavorites by viewModel.showFavorites.collectAsState()

        val categories = listOf("All") + Constants.QuoteCategories.all.map {
            it.replaceFirstChar { c -> c.uppercase() }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Daily Quotes",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { viewModel.toggleShowFavorites() }) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Show favorites",
                                tint = if (showFavorites)
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            when (val s = state) {
                is UiState.Loading -> LoadingContent(modifier = Modifier.padding(paddingValues))
                is UiState.Empty -> EmptyContent(
                    message = if (showFavorites) "No favorite quotes yet" else "No quotes available",
                    modifier = Modifier.padding(paddingValues)
                )
                is UiState.Error -> ErrorContent(
                    message = s.message,
                    onRetry = { viewModel.retry() },
                    modifier = Modifier.padding(paddingValues)
                )
                is UiState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Category filter
                        item {
                            ScrollableTabRow(
                                selectedTabIndex = categories.indexOf(
                                    selectedCategory?.replaceFirstChar { it.uppercase() } ?: "All"
                                ),
                                edgePadding = 0.dp,
                                containerColor = MaterialTheme.colorScheme.background
                            ) {
                                categories.forEachIndexed { index, category ->
                                    Tab(
                                        selected = (selectedCategory?.replaceFirstChar { it.uppercase() } ?: "All") == category,
                                        onClick = {
                                            viewModel.setCategory(
                                                if (category == "All") null
                                                else category.lowercase()
                                            )
                                        },
                                        text = { Text(category) }
                                    )
                                }
                            }
                        }

                        items(s.data) { quote ->
                            QuoteCard(
                                quote = quote,
                                onFavoriteClick = { viewModel.toggleFavorite(it) },
                                onShareClick = { /* Share functionality */ }
                            )
                        }
                    }
                }
            }
        }
    }
}
