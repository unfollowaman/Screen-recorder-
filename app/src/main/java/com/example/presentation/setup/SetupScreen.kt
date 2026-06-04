package com.example.presentation.setup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.components.NeumorphicButton
import com.example.ui.components.NeumorphicCard

@Composable
fun SetupScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Recording Setup",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        NeumorphicCard(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
            Column {
                Text(text = "Resolution: 1080p", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Storage: Internal/Movies", style = MaterialTheme.typography.bodyLarge)
            }
        }

        NeumorphicButton(
            text = "Confirm & Request Permissions",
            onClick = {
                // In a real implementation this would trigger a permission launcher
                // and start the foreground service for recording
            },
            modifier = Modifier.fillMaxWidth().height(64.dp)
        )
    }
}
