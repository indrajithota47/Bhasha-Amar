package com.example.ui.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.data.repository.VoiceCatalog
import com.example.service.PronunciationRule
import com.example.ui.components.AudioPlayerCard
import com.example.ui.theme.*
import com.example.ui.viewmodel.VoiceStudioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: VoiceStudioViewModel,
    onNavigateToVoices: () -> Unit,
    onNavigateToOdiaLab: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val scrollState = rememberScrollState()

    val inputText by viewModel.inputText.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    val isAutoDetect by viewModel.isAutoDetect.collectAsState()
    val detectionResult by viewModel.detectionResult.collectAsState()
    val selectedVoice by viewModel.selectedVoice.collectAsState()
    val selectedEmotion by viewModel.selectedEmotion.collectAsState()
    val selectedStyle by viewModel.selectedSpeakingStyle.collectAsState()
    val selectedAccent by viewModel.selectedOdiaAccent.collectAsState()
    val speedRate by viewModel.speedRate.collectAsState()
    val pitch by viewModel.pitch.collectAsState()
    val energy by viewModel.energy.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val generatedResult by viewModel.generatedResult.collectAsState()
    val previewingVoiceId by viewModel.previewingVoiceId.collectAsState()
    val playerState by viewModel.audioPlayerManager.playerState.collectAsState()
    val customPronunciations by viewModel.customPronunciations.collectAsState()

    var showAdvancedControls by remember { mutableStateOf(false) }
    var showPronunciationDialog by remember { mutableStateOf(false) }
    var wordInput by remember { mutableStateOf("") }
    var replacementInput by remember { mutableStateOf("") }

    val charCount = inputText.length
    val wordCount = if (inputText.isBlank()) 0 else inputText.trim().split(Regex("\\s+")).size

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "भाषा अमर",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.ExtraBold,
                                color = IndigoPrimary
                            )
                            Text(
                                text = " | Bhasha Amar",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "Professional Multilingual Voice Studio",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = OdiaGold.copy(alpha = 0.15f),
                        border = BorderStroke(1.dp, OdiaGold.copy(alpha = 0.4f)),
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onNavigateToOdiaLab() }
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text("✨ ଓଡ଼ିଆ Lab", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = OdiaGoldDark)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // 1. Language Selector Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Language Selector",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { viewModel.setAutoDetect(!isAutoDetect) }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "🌐 Auto Detect",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isAutoDetect) IndigoPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = if (isAutoDetect) FontWeight.Bold else FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Switch(
                        checked = isAutoDetect,
                        onCheckedChange = { viewModel.setAutoDetect(it) },
                        modifier = Modifier.scale(0.7f)
                    )
                }
            }

            if (isAutoDetect && detectionResult != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(IndigoPrimary.copy(alpha = 0.1f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "Detected: ${detectionResult?.detectedLanguage?.displayName} (${(detectionResult!!.confidence * 100).toInt()}%)",
                        style = MaterialTheme.typography.labelSmall,
                        color = IndigoPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (detectionResult!!.isMixed) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• Mixed Text Supported",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 4 Priority Language Pills
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SupportedLanguage.entries.forEach { lang ->
                    val isSelected = selectedLanguage == lang
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = when {
                            isSelected && lang.isFlagship -> OdiaGold.copy(alpha = 0.2f)
                            isSelected -> IndigoPrimary.copy(alpha = 0.15f)
                            else -> MaterialTheme.colorScheme.surfaceVariant
                        },
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = when {
                                isSelected && lang.isFlagship -> OdiaGold
                                isSelected -> IndigoPrimary
                                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                            }
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { viewModel.selectLanguage(lang) }
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp)
                        ) {
                            Text(
                                text = lang.nativeName,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            if (lang.isFlagship) {
                                Text(
                                    text = "★ Flagship",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = OdiaGoldDark
                                )
                            } else {
                                Text(
                                    text = lang.displayName,
                                    fontSize = 10.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Main Text Editor Area
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { viewModel.onInputTextChanged(it) },
                        placeholder = {
                            Text(
                                text = selectedLanguage.defaultPlaceholder,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 140.dp, max = 240.dp)
                            .testTag("text_input_field"),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                    Spacer(modifier = Modifier.height(8.dp))

                    // Text Action Toolbar & Counters
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Counters
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "$charCount chars",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = " • $wordCount words",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Actions: Clear, Paste, Example
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (inputText.isNotEmpty()) {
                                TextButton(
                                    onClick = { viewModel.onInputTextChanged("") },
                                    contentPadding = PaddingValues(horizontal = 6.dp)
                                ) {
                                    Text("Clear", fontSize = 12.sp)
                                }
                            }

                            TextButton(
                                onClick = {
                                    val clip = clipboardManager.getText()?.text
                                    if (!clip.isNullOrBlank()) {
                                        viewModel.onInputTextChanged(clip)
                                    } else {
                                        Toast.makeText(context, "Clipboard is empty", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                contentPadding = PaddingValues(horizontal = 6.dp)
                            ) {
                                Text("Paste", fontSize = 12.sp)
                            }

                            TextButton(
                                onClick = {
                                    val samples = selectedLanguage.sampleSentences
                                    val nextSample = samples.random()
                                    viewModel.onInputTextChanged(nextSample)
                                },
                                contentPadding = PaddingValues(horizontal = 6.dp)
                            ) {
                                Text("Example", fontSize = 12.sp, color = IndigoPrimary)
                            }
                        }
                    }

                    // Quick Pause Inserter Pills
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Pause:",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        listOf(
                            "[Short Pause]" to "Short (0.3s)",
                            "[Medium Pause]" to "Med (0.7s)",
                            "[Long Pause]" to "Long (1.5s)"
                        ).forEach { (tag, label) ->
                            SuggestionChip(
                                onClick = { viewModel.insertPauseTag(tag) },
                                label = { Text(label, fontSize = 11.sp) },
                                modifier = Modifier.padding(end = 4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 3. Selected Voice Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Selected Voice",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                TextButton(onClick = onNavigateToVoices) {
                    Text("Browse All Voices (${VoiceCatalog.getVoicesForLanguage(selectedLanguage).size})", fontSize = 12.sp, color = IndigoPrimary)
                }
            }

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(Color(selectedVoice.avatarColorHex)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(selectedVoice.gender.icon, fontSize = 22.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = selectedVoice.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                if (selectedVoice.isFlagshipOdia) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("⭐ Odia", fontSize = 10.sp, color = OdiaGoldDark, fontWeight = FontWeight.Bold)
                                }
                            }
                            Text(
                                text = "${selectedVoice.gender.displayName} • ${selectedVoice.ageGroup.displayName} • ${selectedVoice.accentOrVariant}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    // Preview Button
                    val isPreviewing = previewingVoiceId == selectedVoice.id
                    FilledTonalIconButton(
                        onClick = {
                            if (isPreviewing) {
                                viewModel.stopPreview()
                            } else {
                                viewModel.previewVoice(selectedVoice)
                            }
                        },
                        modifier = Modifier.testTag("voice_preview_button")
                    ) {
                        Icon(
                            imageVector = if (isPreviewing) Icons.Default.Stop else Icons.Default.VolumeUp,
                            contentDescription = "Preview Voice",
                            tint = if (isPreviewing) CoralRose else IndigoPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 4. Emotion Studio Section (18 Emotions)
            Text(
                text = "Emotion Studio",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(Emotion.entries) { emotion ->
                    val isSelected = selectedEmotion == emotion
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectEmotion(emotion) },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(emotion.emoji, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(emotion.displayName, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StudioViolet.copy(alpha = 0.2f),
                            selectedLabelColor = StudioVioletGlow
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 5. Speaking Style Section (15 Styles)
            Text(
                text = "Speaking Style",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(SpeakingStyle.entries) { style ->
                    val isSelected = selectedStyle == style
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectSpeakingStyle(style) },
                        label = {
                            Text(style.displayName, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = CyberCyan.copy(alpha = 0.2f),
                            selectedLabelColor = CyberCyan
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 6. Advanced Voice Controls
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showAdvancedControls = !showAdvancedControls },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Tune, contentDescription = null, tint = IndigoPrimary, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Advanced Voice Controls",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Icon(
                            imageVector = if (showAdvancedControls) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }

                    AnimatedVisibility(visible = showAdvancedControls) {
                        Column(modifier = Modifier.padding(top = 12.dp)) {
                            // Speed Slider
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Speed Rate", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                Text("${String.format("%.2f", speedRate)}x", style = MaterialTheme.typography.bodySmall, color = IndigoPrimary, fontWeight = FontWeight.Bold)
                            }
                            Slider(
                                value = speedRate,
                                onValueChange = { viewModel.setSpeed(it) },
                                valueRange = 0.5f..2.0f,
                                steps = 5
                            )

                            // Pitch Slider
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Voice Pitch", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                Text(
                                    when {
                                        pitch < 0.7f -> "Very Low"
                                        pitch < 0.9f -> "Low"
                                        pitch in 0.9f..1.1f -> "Normal"
                                        pitch <= 1.4f -> "High"
                                        else -> "Very High"
                                    },
                                    style = MaterialTheme.typography.bodySmall,
                                    color = IndigoPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Slider(
                                value = pitch,
                                onValueChange = { viewModel.setPitch(it) },
                                valueRange = 0.5f..1.8f
                            )

                            // Voice Energy
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Voice Energy:", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    listOf("Low", "Medium", "High").forEach { e ->
                                        FilterChip(
                                            selected = energy == e,
                                            onClick = { viewModel.setEnergy(e) },
                                            label = { Text(e, fontSize = 11.sp) }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            // Custom Pronunciation Button
                            OutlinedButton(
                                onClick = { showPronunciationDialog = true },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Spellcheck, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Pronunciation Editor (${customPronunciations.size} custom rules)", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 7. Large Premium Glowing CTA Button
            Button(
                onClick = {
                    viewModel.generateVoice { result ->
                        Toast.makeText(context, "Voice generated successfully!", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(58.dp)
                    .testTag("generate_voice_button"),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    SoftGoldDark,
                                    SoftGold,
                                    SoftGoldAmber,
                                    SoftGoldLuminous
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isGenerating) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                color = Color(0xFF241D12),
                                modifier = Modifier.size(22.dp),
                                strokeWidth = 2.5.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Synthesizing AI Speech...",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF241D12)
                            )
                        }
                    } else {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = null,
                                tint = Color(0xFF241D12),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "GENERATE VOICE",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF241D12),
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 8. Generated Audio Player
            generatedResult?.let { result ->
                AudioPlayerCard(
                    result = result,
                    playerState = playerState,
                    voice = selectedVoice,
                    onTogglePlayPause = { viewModel.audioPlayerManager.togglePlayPause() },
                    onSeek = { viewModel.audioPlayerManager.seekTo(it) },
                    onSkipForward = { viewModel.audioPlayerManager.skipForward(10000L) },
                    onSkipBackward = { viewModel.audioPlayerManager.skipBackward(10000L) },
                    onReplay = {
                        viewModel.audioPlayerManager.seekTo(0)
                        if (!playerState.isPlaying) viewModel.audioPlayerManager.togglePlayPause()
                    },
                    onSpeedChange = { viewModel.audioPlayerManager.setPlaybackSpeed(it) },
                    onShare = {
                        viewModel.audioPlayerManager.shareAudio(result.file, "Bhasha Amar - ${selectedVoice.name}")
                    },
                    onSaveProject = {
                        viewModel.saveCurrentToProjects()
                        Toast.makeText(context, "Saved to My Projects!", Toast.LENGTH_SHORT).show()
                    },
                    onExportAudio = { title ->
                        viewModel.audioPlayerManager.exportAudio(result.file, title)
                    },
                    onRegenerate = {
                        viewModel.generateVoice()
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    // Pronunciation Editor Dialog
    if (showPronunciationDialog) {
        AlertDialog(
            onDismissRequest = { showPronunciationDialog = false },
            title = { Text("Pronunciation Editor") },
            text = {
                Column {
                    Text(
                        text = "Modify how specific words or names are spoken by replacing with phonetic text (e.g. OpenAI → ओपन एआई / ଓପେନ୍ ଏ ଆଇ):",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = wordInput,
                        onValueChange = { wordInput = it },
                        label = { Text("Original Word") },
                        placeholder = { Text("e.g. OpenAI") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = replacementInput,
                        onValueChange = { replacementInput = it },
                        label = { Text("Custom Pronunciation / Phonetics") },
                        placeholder = { Text("e.g. ଓପେନ୍ ଏ ଆଇ") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Active Custom Rules:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (customPronunciations.isEmpty()) {
                        Text(
                            text = "No custom rules added yet. Built-in tech rules are applied automatically.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        customPronunciations.forEach { rule ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("${rule.originalWord} → ${rule.replacementWord}", fontSize = 12.sp)
                                IconButton(
                                    onClick = { viewModel.removePronunciationRule(rule) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Delete", modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (wordInput.isNotBlank() && replacementInput.isNotBlank()) {
                            viewModel.addPronunciationRule(wordInput, replacementInput)
                            wordInput = ""
                            replacementInput = ""
                        }
                        showPronunciationDialog = false
                    }
                ) {
                    Text("Add & Close")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPronunciationDialog = false }) {
                    Text("Close")
                }
            }
        )
    }
}
