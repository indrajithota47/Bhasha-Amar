package com.example.data.model

enum class SpeakingStyle(
    val displayName: String,
    val iconName: String,
    val description: String,
    val tempoFactor: Float,
    val intonationFactor: Float
) {
    NORMAL_CONVERSATION("Normal Conversation", "chat", "Natural everyday speech flow", 1.0f, 1.0f),
    PROFESSIONAL("Professional", "business", "Formal, crisp business communication", 0.98f, 0.95f),
    NEWS_READER("News Reader", "newspaper", "Clear broadcaster cadence with neutral precision", 1.05f, 0.9f),
    STORYTELLING("Storytelling", "auto_stories", "Expressive dramatic pacing for audiobooks & folk tales", 0.92f, 1.25f),
    PODCAST("Podcast", "podcasts", "Engaging, conversational, intimate microphone proximity", 0.96f, 1.1f),
    TEACHER("Teacher", "school", "Measured, articulate, instructional clarity", 0.88f, 1.05f),
    PUBLIC_SPEECH("Public Speech", "campaign", "Resonant projection, emphatic oratorical pauses", 0.94f, 1.2f),
    MOTIVATIONAL_SPEAKER("Motivational Speaker", "local_fire_department", "High-energy inspiration with strong climaxes", 1.08f, 1.3f),
    SOCIAL_MEDIA_VOICE("Social Media Voice", "trending_up", "Punchy, fast-paced, hooking intonation", 1.15f, 1.15f),
    YOUTUBE_NARRATION("YouTube Narration", "smart_display", "Dynamic, captivating, easy to follow long-form voice", 1.04f, 1.12f),
    DOCUMENTARY("Documentary", "movie_filter", "Deep, solemn, atmospheric curiosity & authority", 0.90f, 1.15f),
    ADVERTISEMENT("Advertisement", "campaign", "Upbeat, persuasive, polished commercial energy", 1.12f, 1.25f),
    CINEMATIC("Cinematic", "theaters", "Rich movie-trailer resonance with theatrical gravity", 0.86f, 1.35f),
    CASUAL("Casual", "sentiment_satisfied", "Relaxed, informal cadence with modern ease", 1.02f, 1.0f),
    FRIENDLY_CONVERSATION("Friendly Conversation", "forum", "Warm companion tone, gentle smile inflections", 0.98f, 1.08f);

    companion object {
        val DEFAULT = NORMAL_CONVERSATION
    }
}
