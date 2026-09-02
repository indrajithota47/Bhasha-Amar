package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.theme.*
import kotlin.math.sin

@Composable
fun AudioWaveformVisualizer(
    waveformData: List<Float>,
    currentPositionMs: Long,
    totalDurationMs: Long,
    isPlaying: Boolean,
    onSeekPosition: (Long) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 64.dp,
    activeColor: Color = IndigoPrimary,
    inactiveColor: Color = Color.Gray.copy(alpha = 0.35f)
) {
    val progress = remember(currentPositionMs, totalDurationMs) {
        if (totalDurationMs > 0) (currentPositionMs.toFloat() / totalDurationMs.toFloat()).coerceIn(0f, 1f)
        else 0f
    }

    // Gentle pulse animation when audio is playing
    val infiniteTransition = rememberInfiniteTransition(label = "wave_pulse")
    val pulsePhase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 6.28f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse_phase"
    )

    val activeBrush = Brush.horizontalGradient(
        listOf(
            SoftGoldLight,
            SoftGold,
            SoftGoldAmber,
            SoftGoldLuminous
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(12.dp))
            .background(GoldDarkCardElevated.copy(alpha = 0.6f))
            .pointerInput(totalDurationMs) {
                detectTapGestures { offset ->
                    val fraction = (offset.x / size.width.toFloat()).coerceIn(0f, 1f)
                    val targetMs = (fraction * totalDurationMs).toLong()
                    onSeekPosition(targetMs)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val barsCount = 48
            val spacing = 3.dp.toPx()
            val totalSpacing = spacing * (barsCount - 1)
            val barWidth = (size.width - totalSpacing) / barsCount

            val dataPoints = if (waveformData.isNotEmpty()) {
                waveformData
            } else {
                List(barsCount) { index ->
                    0.2f + 0.6f * kotlin.math.abs(sin(index * 0.4f)).toFloat()
                }
            }

            for (i in 0 until barsCount) {
                val dataIndex = ((i.toFloat() / barsCount) * dataPoints.size).toInt().coerceIn(0, dataPoints.size - 1)
                var amplitude = dataPoints.getOrElse(dataIndex) { 0.3f }

                if (isPlaying) {
                    val waveMod = 0.12f * sin(i * 0.35f + pulsePhase).toFloat()
                    amplitude = (amplitude + waveMod).coerceIn(0.1f, 1.0f)
                }

                val barHeight = (size.height * 0.85f * amplitude).coerceAtLeast(4.dp.toPx())
                val left = i * (barWidth + spacing)
                val top = (size.height - barHeight) / 2f

                val barFraction = i.toFloat() / barsCount
                val isBarActive = barFraction <= progress

                drawRoundRect(
                    brush = if (isBarActive) activeBrush else Brush.linearGradient(listOf(inactiveColor, inactiveColor)),
                    topLeft = Offset(left, top),
                    size = Size(barWidth, barHeight),
                    cornerRadius = CornerRadius(barWidth / 2, barWidth / 2)
                )
            }

            // Draw current scrubber needle in Soft Gold
            val scrubberX = progress * size.width
            drawLine(
                color = SoftGold,
                start = Offset(scrubberX, 0f),
                end = Offset(scrubberX, size.height),
                strokeWidth = 2.5.dp.toPx()
            )
            drawCircle(
                color = SoftGoldLight,
                radius = 5.dp.toPx(),
                center = Offset(scrubberX, size.height / 2f)
            )
            drawCircle(
                color = SoftGold,
                radius = 3.dp.toPx(),
                center = Offset(scrubberX, size.height / 2f)
            )
        }
    }
}
