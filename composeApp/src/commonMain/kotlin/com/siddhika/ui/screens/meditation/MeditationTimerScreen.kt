package com.siddhika.ui.screens.meditation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.siddhika.ui.components.TimerCircle
import com.siddhika.ui.components.TimerState
import org.koin.core.parameter.parametersOf

data class MeditationTimerScreen(val meditationId: Long) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<MeditationTimerViewModel> { parametersOf(meditationId) }

        val meditation by viewModel.meditation.collectAsState()
        val remainingSeconds by viewModel.remainingSeconds.collectAsState()
        val totalSeconds by viewModel.totalSeconds.collectAsState()
        val timerState by viewModel.timerState.collectAsState()

        DisposableEffect(Unit) {
            onDispose {
                viewModel.stopTimer()
            }
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = meditation?.title ?: "Meditation",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            viewModel.stopTimer()
                            navigator.pop()
                        }) {
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
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                meditation?.let { med ->
                    Text(
                        text = med.description,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(48.dp))

                TimerCircle(
                    remainingSeconds = remainingSeconds,
                    totalSeconds = totalSeconds,
                    timerState = timerState
                )

                Spacer(modifier = Modifier.height(48.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Reset button
                    IconButton(
                        onClick = { viewModel.resetTimer() },
                        enabled = timerState != TimerState.Idle
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset",
                            modifier = Modifier.size(32.dp),
                            tint = if (timerState != TimerState.Idle)
                                MaterialTheme.colorScheme.onSurfaceVariant
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        )
                    }

                    // Play/Pause button
                    FilledIconButton(
                        onClick = {
                            when (timerState) {
                                TimerState.Idle, TimerState.Paused -> viewModel.startTimer()
                                TimerState.Running -> viewModel.pauseTimer()
                                TimerState.Completed -> viewModel.resetTimer()
                            }
                        },
                        modifier = Modifier.size(80.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Icon(
                            imageVector = when (timerState) {
                                TimerState.Running -> Icons.Default.Pause
                                TimerState.Completed -> Icons.Default.Refresh
                                else -> Icons.Default.PlayArrow
                            },
                            contentDescription = when (timerState) {
                                TimerState.Running -> "Pause"
                                TimerState.Completed -> "Restart"
                                else -> "Start"
                            },
                            modifier = Modifier.size(40.dp)
                        )
                    }

                    // Stop button
                    IconButton(
                        onClick = {
                            viewModel.stopTimer()
                            navigator.pop()
                        },
                        enabled = timerState != TimerState.Idle
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Stop",
                            modifier = Modifier.size(32.dp),
                            tint = if (timerState != TimerState.Idle)
                                MaterialTheme.colorScheme.error
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                        )
                    }
                }

                if (timerState == TimerState.Completed) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Well done! Session completed.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
