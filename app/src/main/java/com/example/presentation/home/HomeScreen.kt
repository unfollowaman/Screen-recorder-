package com.example.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.RecordingDao
import com.example.data.RecordingEntity
import com.example.ui.components.NeumorphicButton
import com.example.ui.theme.AppTypography
import kotlinx.coroutines.flow.Flow

class HomeViewModel(private val recordingDao: RecordingDao) : ViewModel() {
    val recordings: Flow<List<RecordingEntity>> = recordingDao.getAllRecordings()

    class Factory(private val recordingDao: RecordingDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(recordingDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

@Composable
fun HomeScreen(
    recordingDao: RecordingDao,
    onStartRecordingClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory(recordingDao))
    val recordings by viewModel.recordings.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to ScreenStudio",
            style = AppTypography.titleLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Large Prominent Start Recording CTA
        NeumorphicButton(
            text = "Start Recording",
            onClick = onStartRecordingClicked,
            modifier = Modifier.fillMaxWidth().height(64.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Recent Recordings
        Text(
            text = "Recent Recordings",
            style = AppTypography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(recordings) { recording ->
                RecordingCard(recording)
            }
            if (recordings.isEmpty()) {
                item {
                    Text(
                        text = "No recordings yet. Start your first presentation!",
                        style = AppTypography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun RecordingCard(recording: RecordingEntity) {
    com.example.ui.components.NeumorphicCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column {
            Text(text = "Recording ${recording.createdAt}", style = AppTypography.bodyLarge)
            Text(text = "${recording.resolution} @ ${recording.fps} fps", style = AppTypography.labelSmall)
            Text(text = "Duration: ${recording.durationMs / 1000}s", style = AppTypography.labelSmall)
        }
    }
}
