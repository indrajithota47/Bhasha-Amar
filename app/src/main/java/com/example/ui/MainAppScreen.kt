package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.IndigoPrimary
import com.example.ui.theme.OdiaGold
import com.example.ui.theme.OdiaGoldDark
import com.example.ui.viewmodel.VoiceStudioViewModel

enum class StudioTab(val title: String, val icon: ImageVector, val isSpecial: Boolean = false) {
    STUDIO("Studio", Icons.Default.GraphicEq),
    VOICES("Voices", Icons.Default.RecordVoiceOver),
    ODIA_LAB("ଓଡ଼ିଆ Lab", Icons.Default.Stars, isSpecial = true),
    PROJECTS("Projects", Icons.Default.Folder),
    SETTINGS("About", Icons.Default.Info)
}

@Composable
fun MainAppScreen(
    viewModel: VoiceStudioViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    var showSplash by remember { mutableStateOf(true) }
    var currentTab by remember { mutableStateOf(StudioTab.STUDIO) }

    if (showSplash) {
        SplashScreen(
            onSplashFinished = { showSplash = false }
        )
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp,
                    modifier = Modifier.testTag("bottom_nav_bar")
                ) {
                    StudioTab.entries.forEach { tab ->
                        val isSelected = currentTab == tab
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = { currentTab = tab },
                            icon = {
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title,
                                    tint = when {
                                        tab.isSpecial && isSelected -> OdiaGoldDark
                                        tab.isSpecial -> OdiaGold
                                        isSelected -> IndigoPrimary
                                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            },
                            label = {
                                Text(
                                    text = tab.title,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (tab.isSpecial && isSelected) OdiaGoldDark else if (isSelected) IndigoPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            },
                            modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                        )
                    }
                }
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                AnimatedContent(
                    targetState = currentTab,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "tab_transition"
                ) { tab ->
                    when (tab) {
                        StudioTab.STUDIO -> HomeScreen(
                            viewModel = viewModel,
                            onNavigateToVoices = { currentTab = StudioTab.VOICES },
                            onNavigateToOdiaLab = { currentTab = StudioTab.ODIA_LAB }
                        )
                        StudioTab.VOICES -> VoicesScreen(
                            viewModel = viewModel,
                            onVoiceSelected = { currentTab = StudioTab.STUDIO }
                        )
                        StudioTab.ODIA_LAB -> OdiaLabScreen(
                            viewModel = viewModel,
                            onApplyToStudio = { currentTab = StudioTab.STUDIO }
                        )
                        StudioTab.PROJECTS -> ProjectsScreen(
                            viewModel = viewModel,
                            onLoadIntoStudio = { currentTab = StudioTab.STUDIO }
                        )
                        StudioTab.SETTINGS -> SettingsScreen()
                    }
                }
            }
        }
    }
}
