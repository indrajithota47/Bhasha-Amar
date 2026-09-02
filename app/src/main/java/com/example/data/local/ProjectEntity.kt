package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val text: String,
    val languageCode: String,
    val voiceId: String,
    val voiceName: String,
    val voiceGender: String,
    val emotion: String,
    val style: String,
    val accent: String,
    val speed: Float,
    val pitch: Float,
    val energy: String,
    val audioFilePath: String,
    val durationMs: Long,
    val createdAt: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false,
    val format: String = "WAV"
)
