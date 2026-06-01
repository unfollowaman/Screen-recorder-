package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AccentPrimary
import com.example.ui.theme.BgBase

@Composable
fun NeumorphicButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isActive: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val pressed = isPressed || isActive

    val background = if (pressed) BgBase else BgBase
    
    // Inset shadow simulation for pressed state could be done via a custom modifier
    // For simplicity, we just change the shadow modifiers if it's pressed
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(Color.Transparent)
            .then(
                if (pressed) Modifier // A flat or inset shadow mod
                else Modifier.neumorphicShadow(radius = 50.dp)
            )
            .clip(RoundedCornerShape(50.dp))
            .background(background)
            .clickable(
                interactionSource = interactionSource,
                indication = null, 
                onClick = onClick
            )
    ) {
        // Content
        Text(text = text, color = if(pressed) AccentPrimary else Color.Black)
    }
}
