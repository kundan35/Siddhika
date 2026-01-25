package com.siddhika.ui.screens.prayers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.siddhika.ui.components.SiddhikaButton
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalTime

data class AddReminderScreen(
    val prayerId: Long? = null,
    val defaultTitle: String? = null
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<AddReminderViewModel>()

        var title by remember { mutableStateOf(defaultTitle ?: "") }
        var hour by remember { mutableStateOf("08") }
        var minute by remember { mutableStateOf("00") }
        val selectedDays = remember { mutableStateListOf<DayOfWeek>() }

        val daysOfWeek = DayOfWeek.entries

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Add Reminder",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close"
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Reminder Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Time",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Simple time input (hour:minute)
                androidx.compose.foundation.layout.Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = hour,
                        onValueChange = {
                            if (it.length <= 2 && it.all { c -> c.isDigit() }) {
                                val h = it.toIntOrNull() ?: 0
                                if (h in 0..23) hour = it
                            }
                        },
                        label = { Text("Hour") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = minute,
                        onValueChange = {
                            if (it.length <= 2 && it.all { c -> c.isDigit() }) {
                                val m = it.toIntOrNull() ?: 0
                                if (m in 0..59) minute = it
                            }
                        },
                        label = { Text("Minute") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Repeat on",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    daysOfWeek.forEach { day ->
                        FilterChip(
                            selected = selectedDays.contains(day),
                            onClick = {
                                if (selectedDays.contains(day)) {
                                    selectedDays.remove(day)
                                } else {
                                    selectedDays.add(day)
                                }
                            },
                            label = {
                                Text(day.name.take(3).lowercase().replaceFirstChar { it.uppercase() })
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                SiddhikaButton(
                    text = "Save Reminder",
                    onClick = {
                        val h = hour.toIntOrNull() ?: 8
                        val m = minute.toIntOrNull() ?: 0
                        viewModel.saveReminder(
                            title = title.ifBlank { "Prayer Reminder" },
                            time = LocalTime(h, m),
                            daysOfWeek = selectedDays.toList(),
                            prayerId = prayerId
                        )
                        navigator.pop()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = title.isNotBlank() || defaultTitle != null
                )
            }
        }
    }
}
