package com.siddhika.ui.screens.quotes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.siddhika.core.util.Constants
import com.siddhika.ui.components.QuoteCard

class QuotesListScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<QuotesViewModel>()
        val quotes by viewModel.quotes.collectAsState()
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
            if (quotes.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (showFavorites) "No favorite quotes yet" else "No quotes available",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
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

                    items(quotes) { quote ->
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
