package com.example.data.model

enum class Gender(val displayName: String, val icon: String) {
    MALE("Male", "👨"),
    FEMALE("Female", "👩"),
    CHILD("Child", "🧒")
}

enum class AgeGroup(val displayName: String) {
    CHILD("Child (5-11)"),
    TEEN("Teen (12-19)"),
    YOUNG("Young (20-35)"),
    ADULT("Adult (36-55)"),
    ELDERLY("Elderly (56+)")
}

enum class VoiceStyle(val displayName: String) {
    PROFESSIONAL("Professional"),
    NARRATOR("Narrator"),
    DEEP("Deep & Rich"),
    SOFT("Soft & Gentle"),
    ENERGETIC("Energetic"),
    CASUAL("Casual / Natural"),
    STORYTELLING("Storytelling"),
    TRADITIONAL("Traditional / Classic"),
    CUTE("Cute & Playful")
}

data class Voice(
    val id: String,
    val name: String,
    val language: SupportedLanguage,
    val gender: Gender,
    val ageGroup: AgeGroup,
    val accentOrVariant: String,
    val style: VoiceStyle,
    val description: String,
    val pitchMultiplier: Float = 1.0f,
    val rateMultiplier: Float = 1.0f,
    val sampleText: String,
    val isFlagshipOdia: Boolean = false,
    val avatarColorHex: Long = 0xFF6366F1
) {
    val sampleDialogue: String get() = sampleText
}
