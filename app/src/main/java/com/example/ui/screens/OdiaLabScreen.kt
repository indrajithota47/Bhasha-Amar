package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.data.repository.VoiceCatalog
import com.example.ui.theme.*
import com.example.ui.viewmodel.VoiceStudioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OdiaLabScreen(
    viewModel: VoiceStudioViewModel,
    onApplyToStudio: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val selectedAccent by viewModel.selectedOdiaAccent.collectAsState()
    val previewingVoiceId by viewModel.previewingVoiceId.collectAsState()
    val odiaVoices = remember { VoiceCatalog.getVoicesForLanguage(SupportedLanguage.ODIA) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ଓଡ଼ିଆ Dialect & Voice Lab",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = OdiaGold.copy(alpha = 0.2f),
                            border = BorderStroke(1.dp, OdiaGoldDark.copy(alpha = 0.5f))
                        ) {
                            Text(
                                text = "Flagship",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = OdiaGoldDark,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Flagship Banner
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color(0xFF2E1065),
                                        Color(0xFF431407),
                                        Color(0xFF1E1B4B)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(OdiaGold.copy(alpha = 0.25f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("ଓ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = OdiaGoldLight)
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "ବିଶେଷ ଓଡ଼ିଆ ଭଏସ୍ ଷ୍ଟୁଡିଓ",
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = OdiaGoldLight
                                    )
                                    Text(
                                        text = "High-precision Odia phonetics & dialect engine",
                                        fontSize = 11.sp,
                                        color = Color(0xFFCBD5E1)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = "Bhasha Amar brings specialized phonetic synthesis for Odia dialects across Kataki (Standard Coastal), Sambalpuri/Koshali, Baleswari (Northern), and Ganjami/Southern expressions.",
                                fontSize = 12.sp,
                                color = Color(0xFFE2E8F0),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // 2. Dialect Accents Selection
            item {
                Text(
                    text = "1. Choose Odia Dialect Accent",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(OdiaAccent.entries) { accent ->
                val isSelected = selectedAccent == accent
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) OdiaGold.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = BorderStroke(
                        width = if (isSelected) 1.5.dp else 1.dp,
                        color = if (isSelected) OdiaGold else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { viewModel.selectOdiaAccent(accent) }
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { viewModel.selectOdiaAccent(accent) },
                                    colors = RadioButtonDefaults.colors(selectedColor = OdiaGoldDark)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Column {
                                    Text(
                                        text = "${accent.odiaLabel} (${accent.title})",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = accent.region,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = OdiaGoldDark,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = accent.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Sample: \"${accent.sampleDialectSentence}\"",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp,
                                    fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                )
                            }
                        }
                    }
                }
            }

            // 3. Odia Voice Showcase
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "2. Specialized Odia AI Voices (${odiaVoices.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(odiaVoices) { voice ->
                val isSelectedVoice = viewModel.selectedVoice.collectAsState().value.id == voice.id
                val isPreviewing = previewingVoiceId == voice.id

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelectedVoice) IndigoPrimary.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = BorderStroke(
                        width = if (isSelectedVoice) 1.5.dp else 1.dp,
                        color = if (isSelectedVoice) IndigoPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                    ),
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
                                    .background(Color(voice.avatarColorHex)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(voice.gender.icon, fontSize = 20.sp)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = voice.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "⭐ Odia",
                                        fontSize = 10.sp,
                                        color = OdiaGoldDark,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = "${voice.gender.displayName} • ${voice.ageGroup.displayName} • ${voice.accentOrVariant}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = voice.description,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            FilledTonalIconButton(
                                onClick = {
                                    if (isPreviewing) viewModel.stopPreview()
                                    else viewModel.previewVoice(voice, selectedAccent.sampleDialectSentence)
                                },
                                modifier = Modifier.size(38.dp)
                            ) {
                                Icon(
                                    imageVector = if (isPreviewing) Icons.Default.Stop else Icons.Default.VolumeUp,
                                    contentDescription = "Preview",
                                    tint = if (isPreviewing) CoralRose else IndigoPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            TextButton(
                                onClick = {
                                    viewModel.selectVoice(voice)
                                    viewModel.onInputTextChanged(selectedAccent.sampleDialectSentence)
                                    onApplyToStudio()
                                    Toast.makeText(context, "Applied ${voice.name} (${selectedAccent.title}) to Studio", Toast.LENGTH_SHORT).show()
                                },
                                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text("Use in Studio", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OdiaGoldDark)
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}
