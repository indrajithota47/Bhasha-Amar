package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Voice
import com.example.service.AudioGenerationResult
import com.example.service.PlayerState
import com.example.ui.theme.*

@Composable
fun AudioPlayerCard(
    result: AudioGenerationResult,
    playerState: PlayerState,
    voice: Voice,
    onTogglePlayPause: () -> Unit,
    onSeek: (Long) -> Unit,
    onSkipForward: () -> Unit,
    onSkipBackward: () -> Unit,
    onReplay: () -> Unit,
    onSpeedChange: (Float) -> Unit,
    onShare: () -> Unit,
    onSaveProject: () -> Unit,
    onExportAudio: (String) -> Unit,
    onRegenerate: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showExportDialog by remember { mutableStateOf(false) }
    var exportTitle by remember { mutableStateOf("BhashaAmar_${voice.name}_${System.currentTimeMillis() / 1000}") }
    var selectedFormat by remember { mutableStateOf("WAV") }

    Card(
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = GoldDarkCard
        ),
        border = BorderStroke(1.dp, SoftGold.copy(alpha = 0.45f)),
        modifier = modifier
            .fillMaxWidth()
            .testTag("audio_player_card")
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            // Header: Voice and Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (playerState.isPlaying) EmeraldSuccess else SoftGold)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (playerState.isPlaying) "Playing Audio" else "Audio Synthesized",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (playerState.isPlaying) EmeraldSuccess else SoftGoldLight
                    )
                }

                // Voice Badge with Soft Gold outline
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = SoftGoldDeepContainer,
                    border = BorderStroke(1.dp, SoftGold.copy(alpha = 0.3f))
                ) {
                    Text(
                        text = "${voice.name} (${voice.gender.displayName})",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = SoftGoldLight,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Waveform Visualizer
            AudioWaveformVisualizer(
                waveformData = result.waveformPoints,
                currentPositionMs = playerState.currentPositionMs,
                totalDurationMs = if (playerState.durationMs > 0) playerState.durationMs else result.durationMs,
                isPlaying = playerState.isPlaying,
                onSeekPosition = onSeek,
                height = 56.dp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Time Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = formatTime(playerState.currentPositionMs),
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldDarkTextSecondary
                )
                Text(
                    text = formatTime(if (playerState.durationMs > 0) playerState.durationMs else result.durationMs),
                    style = MaterialTheme.typography.labelSmall,
                    color = GoldDarkTextSecondary
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Playback Controls Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Replay
                IconButton(onClick = onReplay) {
                    Icon(Icons.Default.Replay, contentDescription = "Replay", tint = GoldDarkTextPrimary)
                }

                // Skip -10s
                IconButton(onClick = onSkipBackward) {
                    Icon(Icons.Default.Replay10, contentDescription = "Back 10s", tint = GoldDarkTextPrimary)
                }

                // Primary Play / Pause Button in Soft Gold
                FilledIconButton(
                    onClick = onTogglePlayPause,
                    modifier = Modifier
                        .size(56.dp)
                        .testTag("player_play_pause_button"),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = SoftGold,
                        contentColor = Color(0xFF241D12)
                    )
                ) {
                    Icon(
                        imageVector = if (playerState.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (playerState.isPlaying) "Pause" else "Play",
                        modifier = Modifier.size(30.dp),
                        tint = Color(0xFF241D12)
                    )
                }

                // Skip +10s
                IconButton(onClick = onSkipForward) {
                    Icon(Icons.Default.Forward10, contentDescription = "Forward 10s", tint = GoldDarkTextPrimary)
                }

                // Speed Switcher
                val speeds = listOf(0.75f, 1.0f, 1.25f, 1.5f, 2.0f)
                var speedIndex by remember { mutableStateOf(1) } // default 1.0x
                TextButton(
                    onClick = {
                        speedIndex = (speedIndex + 1) % speeds.size
                        val newSpeed = speeds[speedIndex]
                        onSpeedChange(newSpeed)
                    }
                ) {
                    Text("${speeds[speedIndex]}x", fontWeight = FontWeight.Bold, color = SoftGold, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = GoldDarkBorder)
            Spacer(modifier = Modifier.height(12.dp))

            // Action Toolbar (Save Project, Share, Export WAV/MP3, Regenerate)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onSaveProject,
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, SoftGold.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = SoftGoldLight),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Icon(Icons.Default.BookmarkAdd, contentDescription = null, modifier = Modifier.size(16.dp), tint = SoftGold)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Save Project", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }

                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IconButton(onClick = onShare) {
                        Icon(Icons.Default.Share, contentDescription = "Share Audio", tint = GoldDarkTextPrimary)
                    }

                    IconButton(onClick = { showExportDialog = true }) {
                        Icon(Icons.Default.Download, contentDescription = "Export Audio", tint = GoldDarkTextPrimary)
                    }

                    IconButton(onClick = onRegenerate) {
                        Icon(Icons.Default.Refresh, contentDescription = "Regenerate", tint = SoftGold)
                    }
                }
            }
        }
    }

    // Export Dialog
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text("Export & Download Audio") },
            text = {
                Column {
                    Text(
                        text = "Choose filename and format for your high-quality studio audio:",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = exportTitle,
                        onValueChange = { exportTitle = it },
                        label = { Text("File Name") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Select Format:", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 6.dp)) {
                        listOf("WAV (Lossless)", "MP3 (Compressed)").forEach { fmt ->
                            val key = if (fmt.startsWith("WAV")) "WAV" else "MP3"
                            FilterChip(
                                selected = selectedFormat == key,
                                onClick = { selectedFormat = key },
                                label = { Text(fmt, fontSize = 11.sp) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onExportAudio("$exportTitle.$selectedFormat")
                        showExportDialog = false
                    }
                ) {
                    Text("Export Audio")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

private fun formatTime(millis: Long): String {
    val totalSeconds = (millis / 1000).coerceAtLeast(0)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}
