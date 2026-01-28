package com.siddhika.ui.screens.prayers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.NotificationAdd
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
import com.siddhika.ui.components.EmptyContent
import com.siddhika.ui.components.ErrorContent
import com.siddhika.ui.components.LoadingContent
import org.koin.core.parameter.parametersOf

data class PrayerDetailScreen(val prayerId: Long) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PrayerDetailViewModel> { parametersOf(prayerId) }
        val state by viewModel.prayer.collectAsState()

        val titleText = when (val s = state) {
            is UiState.Success -> s.data.title
            else -> "Prayer"
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = titleText,
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
                        if (state is UiState.Success) {
                            val p = (state as UiState.Success).data
                            IconButton(onClick = {
                                navigator.push(AddReminderScreen(p.id, p.title))
                            }) {
                                Icon(
                                    imageVector = Icons.Default.NotificationAdd,
                                    contentDescription = "Set Reminder"
                                )
                            }
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
                    message = "Prayer not found",
                    modifier = Modifier.padding(paddingValues)
                )
                is UiState.Error -> ErrorContent(
                    message = s.message,
                    onRetry = { viewModel.retry() },
                    modifier = Modifier.padding(paddingValues)
                )
                is UiState.Success -> {
                    val p = s.data
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = p.category.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = p.content,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.5
                        )
                    }
                }
            }
        }
    }
}
