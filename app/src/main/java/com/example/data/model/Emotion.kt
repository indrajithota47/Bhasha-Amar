package com.example.data.model

enum class Emotion(
    val displayName: String,
    val emoji: String,
    val description: String,
    val pitchDelta: Float,     // Added to base pitch
    val rateDelta: Float,      // Added to base speech rate
    val energyMultiplier: Float, // Volume / intensity
    val pauseMultiplier: Float   // Length of pauses
) {
    NEUTRAL("Neutral", "😐", "Balanced, clear, natural baseline tone", 0.0f, 0.0f, 1.0f, 1.0f),
    HAPPY("Happy", "😊", "Cheerful, uplifting, bright intonation", 0.15f, 0.08f, 1.15f, 0.9f),
    EXCITED("Excited", "🤩", "High energy, fast rhythm, passionate", 0.25f, 0.18f, 1.3f, 0.8f),
    SAD("Sad", "🥺", "Soft, slower, emotionally expressive cadence", -0.15f, -0.18f, 0.8f, 1.4f),
    ANGRY("Angry", "😠", "Punchy intensity, firm pauses, strong volume", -0.05f, 0.12f, 1.35f, 0.85f),
    EMOTIONAL("Emotional", "🫂", "Deep heartfelt expression, lingering cadence", -0.08f, -0.12f, 0.9f, 1.3f),
    CALM("Calm", "😌", "Soothing, relaxed, steady gentle delivery", -0.08f, -0.10f, 0.85f, 1.25f),
    SERIOUS("Serious", "🧐", "Authoritative, deep resonance, formal pauses", -0.12f, -0.05f, 1.1f, 1.2f),
    FRIENDLY("Friendly", "🤗", "Warm, inviting, conversational ease", 0.08f, 0.04f, 1.05f, 0.95f),
    FEARFUL("Fearful", "😨", "Breathier, higher pitch fluctuations, tense rhythm", 0.20f, 0.10f, 0.9f, 0.9f),
    SURPRISED("Surprised", "😲", "Dynamic high inflection peaks, lively rhythm", 0.28f, 0.14f, 1.2f, 0.85f),
    CONFIDENT("Confident", "💪", "Clear, bold articulation, punchy steady tempo", 0.02f, 0.05f, 1.25f, 1.0f),
    ROMANTIC("Romantic / Warm", "💖", "Intimate, warm undertones, soft breathiness", -0.10f, -0.15f, 0.85f, 1.35f),
    DRAMATIC("Dramatic", "🎭", "Theatrical pauses, wide pitch range, heavy emphasis", -0.05f, -0.10f, 1.25f, 1.5f),
    MOTIVATIONAL("Motivational", "🔥", "Inspiring cadence, energetic build-up, punchy finish", 0.12f, 0.08f, 1.3f, 1.05f),
    FUNNY("Funny", "😄", "Playful syncopation, animated pitch shifts", 0.18f, 0.12f, 1.15f, 0.9f),
    WHISPER("Whisper-like", "🤫", "Soft airiness, intimate micro-cadence, muted volume", -0.18f, -0.14f, 0.65f, 1.2f),
    STORYTELLING("Storytelling", "📖", "Captivating rhythmic arcs, narrative suspense pauses", 0.05f, -0.06f, 1.1f, 1.4f);

    companion object {
        val DEFAULT = NEUTRAL
    }
}
