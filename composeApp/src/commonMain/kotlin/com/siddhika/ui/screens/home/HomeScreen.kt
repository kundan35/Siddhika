package com.siddhika.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.siddhika.core.util.UiState
import com.siddhika.ui.components.DailyQuoteCard
import com.siddhika.ui.components.HomeFeatureCard
import com.siddhika.ui.components.LoadingContent
import com.siddhika.ui.screens.auth.ProfileScreen
import com.siddhika.ui.screens.meditation.MeditationListScreen
import com.siddhika.ui.screens.prayers.PrayerListScreen
import com.siddhika.ui.screens.quotes.QuotesListScreen
import com.siddhika.ui.screens.scripture.ScriptureListScreen

class HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<HomeViewModel>()
        val dailyQuoteState by viewModel.dailyQuote.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Siddhika",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    actions = {
                        IconButton(onClick = { navigator.push(ProfileScreen()) }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Daily Quote Card
                when (val s = dailyQuoteState) {
                    is UiState.Loading -> LoadingContent()
                    is UiState.Empty -> { /* No daily quote — skip section */ }
                    is UiState.Error -> { /* Silently skip on home screen */ }
                    is UiState.Success -> {
                        DailyQuoteCard(
                            quote = s.data,
                            onFavoriteClick = { quote ->
                                viewModel.toggleFavorite(quote)
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Explore",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                // Meditation Card
                HomeFeatureCard(
                    title = "Meditation",
                    description = "Find peace through guided meditation sessions",
                    icon = Icons.Default.SelfImprovement,
                    onClick = { navigator.push(MeditationListScreen()) }
                )

                // Quotes Card
                HomeFeatureCard(
                    title = "Daily Quotes",
                    description = "Inspiring wisdom for your spiritual journey",
                    icon = Icons.Default.FormatQuote,
                    onClick = { navigator.push(QuotesListScreen()) }
                )

                // Prayers Card
                HomeFeatureCard(
                    title = "Prayers",
                    description = "Sacred prayers and reminders for your practice",
                    icon = Icons.Default.Notifications,
                    onClick = { navigator.push(PrayerListScreen()) }
                )

                // Scripture Card
                HomeFeatureCard(
                    title = "Scriptures",
                    description = "Read and bookmark sacred texts",
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    onClick = { navigator.push(ScriptureListScreen()) }
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
