package com.example.service

import com.example.data.model.SupportedLanguage

data class LanguageDetectionResult(
    val detectedLanguage: SupportedLanguage,
    val confidence: Float,
    val isMixed: Boolean,
    val odiaRatio: Float,
    val hindiRatio: Float,
    val bengaliRatio: Float,
    val englishRatio: Float
)

object LanguageDetector {

    fun detect(text: String): LanguageDetectionResult {
        if (text.isBlank()) {
            return LanguageDetectionResult(
                detectedLanguage = SupportedLanguage.ODIA,
                confidence = 1.0f,
                isMixed = false,
                odiaRatio = 0f,
                hindiRatio = 0f,
                bengaliRatio = 0f,
                englishRatio = 0f
            )
        }

        var odiaCount = 0
        var hindiCount = 0
        var bengaliCount = 0
        var latinCount = 0
        var totalLetters = 0

        for (char in text) {
            val code = char.code
            when {
                // Odia Unicode block: U+0B00 to U+0B7F
                code in 0x0B00..0x0B7F -> {
                    odiaCount++
                    totalLetters++
                }
                // Bengali Unicode block: U+0980 to U+09FF
                code in 0x0980..0x09FF -> {
                    bengaliCount++
                    totalLetters++
                }
                // Devanagari (Hindi) Unicode block: U+0900 to U+097F
                code in 0x0900..0x097F -> {
                    hindiCount++
                    totalLetters++
                }
                // Latin (English)
                (char in 'a'..'z') || (char in 'A'..'Z') -> {
                    latinCount++
                    totalLetters++
                }
            }
        }

        if (totalLetters == 0) {
            return LanguageDetectionResult(
                detectedLanguage = SupportedLanguage.ODIA,
                confidence = 1.0f,
                isMixed = false,
                odiaRatio = 0f,
                hindiRatio = 0f,
                bengaliRatio = 0f,
                englishRatio = 0f
            )
        }

        val odiaRatio = odiaCount.toFloat() / totalLetters
        val hindiRatio = hindiCount.toFloat() / totalLetters
        val bengaliRatio = bengaliCount.toFloat() / totalLetters
        val englishRatio = latinCount.toFloat() / totalLetters

        // Check if multiple scripts are present in significant amounts (e.g. English + Hindi / Odia)
        val activeCount = listOf(odiaRatio, hindiRatio, bengaliRatio, englishRatio).count { it > 0.15f }
        val isMixed = activeCount > 1

        val detected = when {
            // Flagship priority given to Odia if any Odia characters are present
            odiaRatio >= 0.15f && odiaRatio >= hindiRatio && odiaRatio >= bengaliRatio -> SupportedLanguage.ODIA
            odiaRatio > 0.4f -> SupportedLanguage.ODIA
            bengaliRatio > 0.3f -> SupportedLanguage.BENGALI
            hindiRatio > 0.3f -> SupportedLanguage.HINDI
            englishRatio > 0.4f -> SupportedLanguage.ENGLISH
            odiaCount > 0 -> SupportedLanguage.ODIA
            bengaliCount > 0 -> SupportedLanguage.BENGALI
            hindiCount > 0 -> SupportedLanguage.HINDI
            else -> SupportedLanguage.ODIA
        }

        val maxRatio = maxOf(odiaRatio, hindiRatio, bengaliRatio, englishRatio)

        return LanguageDetectionResult(
            detectedLanguage = detected,
            confidence = (maxRatio * 100).coerceIn(40f, 100f) / 100f,
            isMixed = isMixed,
            odiaRatio = odiaRatio,
            hindiRatio = hindiRatio,
            bengaliRatio = bengaliRatio,
            englishRatio = englishRatio
        )
    }
}
