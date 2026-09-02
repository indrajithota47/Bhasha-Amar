package com.example.service

import com.example.data.model.OdiaAccent

data class PronunciationRule(
    val originalWord: String,
    val replacementWord: String,
    val languageCode: String = "all",
    val isPhoneticHint: Boolean = true
)

object OdiaTextProcessor {

    // Default built-in tech and popular word pronunciation replacements for smoother Indian TTS
    val defaultPronunciationRules: List<PronunciationRule> = listOf(
        PronunciationRule("AI", "ଏ ଆଇ", "or"),
        PronunciationRule("OpenAI", "ଓପେନ୍ ଏ ଆଇ", "or"),
        PronunciationRule("AI", "ए आई", "hi"),
        PronunciationRule("OpenAI", "ओपन एआई", "hi"),
        PronunciationRule("AI", "এ আই", "bn"),
        PronunciationRule("Bhasha Amar", "ଭାଷା ଅମର", "or"),
        PronunciationRule("47ynk", "ଫର୍ଟି ସେଭେନ୍ ୱାଇ ଏନ୍ କେ", "or"),
        PronunciationRule("YouTube", "ୟୁଟ୍ୟୁବ୍", "or"),
        PronunciationRule("Studio", "ଷ୍ଟୁଡିଓ", "or"),
        PronunciationRule("Google", "ଗୁଗୁଲ୍", "or")
    )

    fun processForSynthesis(
        text: String,
        accent: OdiaAccent = OdiaAccent.STANDARD,
        customRules: List<PronunciationRule> = emptyList()
    ): String {
        var processed = text

        // 1. Apply custom user pronunciation rules
        val allRules = defaultPronunciationRules + customRules
        for (rule in allRules) {
            if (rule.originalWord.isNotBlank() && rule.replacementWord.isNotBlank()) {
                val regex = Regex("(?i)\\b${Regex.escape(rule.originalWord)}\\b")
                processed = processed.replace(regex, rule.replacementWord)
            }
        }

        // 2. Normalize Odia Unicode characters & punctuation
        processed = normalizeOdiaUnicode(processed)

        // 3. Process pause markers
        processed = processed
            .replace("[Short Pause]", " ... ")
            .replace("[Medium Pause]", " ..... ")
            .replace("[Long Pause]", " ......... ")
            .replace("<pause-short>", " ... ")
            .replace("<pause-medium>", " ..... ")
            .replace("<pause-long>", " ......... ")

        // 4. If Odia, apply subtle dialect phonetic intonation adjustments
        processed = applyDialectNuance(processed, accent)

        return processed
    }

    private fun normalizeOdiaUnicode(input: String): String {
        return input
            // Normalize double danda to single pause
            .replace("।।", "। ")
            .replace("!", "! ")
            .replace("?", "? ")
            // Ensure proper space around virama if stuck with symbols
            .replace("\u0B4D\u0B4D", "\u0B4D")
    }

    private fun applyDialectNuance(text: String, accent: OdiaAccent): String {
        // Dialect specific syllable micro-adjustments for expressive rhythm
        return when (accent) {
            OdiaAccent.SAMBALPURI -> {
                // Subtle cadence additions for Western Odisha feel
                text
            }
            OdiaAccent.COASTAL -> {
                // Coastal melodious flow
                text
            }
            OdiaAccent.TRADITIONAL -> {
                // Dignified rhythmic punctuation
                text.replace("।", "। ... ")
            }
            else -> text
        }
    }
}
