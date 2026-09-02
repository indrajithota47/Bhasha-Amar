package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.theme.*
import com.example.ui.viewmodel.VoiceStudioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoicesScreen(
    viewModel: VoiceStudioViewModel,
    onVoiceSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredVoices by viewModel.filteredVoices.collectAsState()
    val searchQuery by viewModel.voiceSearchQuery.collectAsState()
    val selectedLanguageFilter by viewModel.voiceLanguageFilter.collectAsState()
    val selectedGenderFilter by viewModel.voiceGenderFilter.collectAsState()
    val selectedAgeFilter by viewModel.voiceAgeFilter.collectAsState()
    val currentSelectedVoice by viewModel.selectedVoice.collectAsState()
    val previewingVoiceId by viewModel.previewingVoiceId.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "🎙 Voice Library",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Multilingual AI Voices across Genders & Styles",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
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
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setVoiceSearchQuery(it) },
                placeholder = { Text("Search voices by name, accent, style...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setVoiceSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_voices_input")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Language Filter Row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    FilterChip(
                        selected = selectedLanguageFilter == null,
                        onClick = { viewModel.setVoiceLanguageFilter(null) },
                        label = { Text("All (${filteredVoices.size})") },
                        shape = RoundedCornerShape(10.dp)
                    )
                }
                items(SupportedLanguage.entries) { lang ->
                    val isSelected = selectedLanguageFilter == lang
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setVoiceLanguageFilter(if (isSelected) null else lang) },
                        label = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(lang.flagEmoji)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(lang.nativeName, fontWeight = if (lang.isFlagship) FontWeight.Bold else FontWeight.Normal)
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = if (lang.isFlagship) FilterChipDefaults.filterChipColors(
                            selectedContainerColor = OdiaGold.copy(alpha = 0.2f),
                            selectedLabelColor = OdiaGoldDark
                        ) else FilterChipDefaults.filterChipColors()
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Gender & Age Filters
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Gender chips
                items(Gender.entries) { gender ->
                    val isSelected = selectedGenderFilter == gender
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setVoiceGenderFilter(if (isSelected) null else gender) },
                        label = { Text("${gender.icon} ${gender.displayName}") },
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                // Age Chips
                items(AgeGroup.entries) { age ->
                    val isSelected = selectedAgeFilter == age
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setVoiceAgeFilter(if (isSelected) null else age) },
                        label = { Text(age.displayName) },
                        shape = RoundedCornerShape(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Voices List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredVoices) { voice ->
                    val isSelected = currentSelectedVoice.id == voice.id
                    val isPreviewing = previewingVoiceId == voice.id

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) IndigoPrimary.copy(alpha = 0.12f) else MaterialTheme.colorScheme.surfaceVariant
                        ),
                        border = BorderStroke(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.selectVoice(voice)
                                onVoiceSelected()
                            }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(CircleShape)
                                            .background(Color(voice.avatarColorHex)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(voice.gender.icon, fontSize = 22.sp)
                                    }
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = voice.name,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            if (voice.isFlagshipOdia) {
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Surface(
                                                    shape = RoundedCornerShape(6.dp),
                                                    color = OdiaGold.copy(alpha = 0.2f)
                                                ) {
                                                    Text(
                                                        text = "★ Odia",
                                                        fontSize = 10.sp,
                                                        color = OdiaGoldDark,
                                                        fontWeight = FontWeight.Bold,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                        Text(
                                            text = "${voice.gender.displayName} • ${voice.ageGroup.displayName} • ${voice.accentOrVariant}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }

                                // Preview Voice Button
                                FilledTonalIconButton(
                                    onClick = {
                                        if (isPreviewing) {
                                            viewModel.stopPreview()
                                        } else {
                                            viewModel.previewVoice(voice)
                                        }
                                    },
                                    modifier = Modifier.size(42.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isPreviewing) Icons.Default.Stop else Icons.Default.VolumeUp,
                                        contentDescription = "Preview ${voice.name}",
                                        tint = if (isPreviewing) CoralRose else IndigoPrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = voice.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.surface
                                ) {
                                    Text(
                                        text = "Style: ${voice.style.displayName}",
                                        style = MaterialTheme.typography.labelSmall,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Button(
                                    onClick = {
                                        viewModel.selectVoice(voice)
                                        onVoiceSelected()
                                    },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (isSelected) EmeraldSuccess else IndigoPrimary
                                    ),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                                    modifier = Modifier.height(34.dp)
                                ) {
                                    Text(
                                        text = if (isSelected) "Active Voice" else "Use Voice",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}
