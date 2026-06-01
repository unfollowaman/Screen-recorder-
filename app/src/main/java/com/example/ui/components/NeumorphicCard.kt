package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.BgBase
import com.example.ui.theme.ShadowDark
import com.example.ui.theme.ShadowLight

fun Modifier.neumorphicShadow(
    radius: Dp = 12.dp,
    offsetX: Dp = 6.dp,
    offsetY: Dp = 6.dp,
    lightShadowColor: Color = ShadowLight,
    darkShadowColor: Color = ShadowDark,
    blurRadius: Dp = 12.dp
): Modifier = this.drawBehind {
    val paint = Paint()
    // Dark Shadow (Bottom Right)
    drawIntoCanvas { canvas ->
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = darkShadowColor.hashCode()
        frameworkPaint.setShadowLayer(
            blurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            darkShadowColor.hashCode()
        )
        canvas.drawRoundRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height,
            radiusX = radius.toPx(),
            radiusY = radius.toPx(),
            paint = paint
        )
    }
    // Light Shadow (Top Left)
    drawIntoCanvas { canvas ->
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = lightShadowColor.hashCode()
        frameworkPaint.setShadowLayer(
            blurRadius.toPx(),
            -offsetX.toPx(),
            -offsetY.toPx(),
            lightShadowColor.hashCode()
        )
        canvas.drawRoundRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height,
            radiusX = radius.toPx(),
            radiusY = radius.toPx(),
            paint = paint
        )
    }
}

@Composable
fun NeumorphicCard(
    modifier: Modifier = Modifier,
    radius: Dp = 20.dp,
    padding: Dp = 16.dp,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .padding(12.dp) // Outer space for shadow
            .neumorphicShadow(radius = radius)
            .clip(RoundedCornerShape(radius))
            .background(BgBase)
            .padding(padding),
        content = content
    )
}
