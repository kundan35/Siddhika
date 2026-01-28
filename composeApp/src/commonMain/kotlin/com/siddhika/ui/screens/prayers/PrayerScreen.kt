package com.siddhika.ui.screens.prayers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.siddhika.ui.components.PrayerCard
import com.siddhika.ui.components.ReminderCard

class PrayerListScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<PrayerViewModel>()
        val prayersState by viewModel.prayers.collectAsState()
        val reminders by viewModel.reminders.collectAsState()

        var selectedTabIndex by remember { mutableIntStateOf(0) }
        val tabs = listOf("Prayers", "Reminders")

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Prayers",
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
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            floatingActionButton = {
                if (selectedTabIndex == 1) {
                    FloatingActionButton(
                        onClick = { navigator.push(AddReminderScreen()) },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Reminder"
                        )
                    }
                }
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = { Text(title) },
                            icon = if (index == 1) {
                                { Icon(Icons.Default.Notifications, null) }
                            } else null
                        )
                    }
                }

                when (selectedTabIndex) {
                    0 -> {
                        when (val s = prayersState) {
                            is UiState.Loading -> LoadingContent()
                            is UiState.Empty -> EmptyContent(message = "No prayers available")
                            is UiState.Error -> ErrorContent(
                                message = s.message,
                                onRetry = { viewModel.retry() }
                            )
                            is UiState.Success -> {
                                LazyColumn(
                                    modifier = Modifier.fillMaxSize(),
                                    contentPadding = PaddingValues(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    items(s.data) { prayer ->
                                        PrayerCard(
                                            prayer = prayer,
                                            onClick = {
                                                navigator.push(PrayerDetailScreen(prayer.id))
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(reminders) { reminder ->
                                ReminderCard(
                                    reminder = reminder,
                                    onToggle = { enabled ->
                                        viewModel.toggleReminder(reminder.id, enabled)
                                    },
                                    onDelete = { viewModel.deleteReminder(reminder.id) }
                                )
                            }

                            if (reminders.isEmpty()) {
                                item {
                                    Spacer(modifier = Modifier.height(48.dp))
                                    Text(
                                        text = "No reminders set.\nTap + to add one.",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
