package com.example.data.model

enum class OdiaAccent(
    val title: String,
    val odiaLabel: String,
    val region: String,
    val description: String,
    val sampleDialectSentence: String,
    val phoneticCharacteristics: String
) {
    STANDARD(
        title = "Standard Odia",
        odiaLabel = "ମାନକ ଓଡ଼ିଆ",
        region = "Central Odisha / State Standard",
        description = "Standard literary Odia with clear syllable enunciation and balanced tempo.",
        sampleDialectSentence = "ନମସ୍କାର, ଓଡ଼ିଶାର କଳା ଓ ସଂସ୍କୃତି ଅତ୍ୟନ୍ତ ଗୌରବମୟ।",
        phoneticCharacteristics = "Clear 'o' endings, balanced retroflex consonants (ଡ, ଢ, ଣ, ଳ), neutral pitch."
    ),
    COASTAL(
        title = "Coastal Odia Style",
        odiaLabel = "ଉପକୂଳୀୟ ଶୈଳୀ",
        region = "Cuttack, Puri, Kendrapara, Jagatsinghpur",
        description = "Melodic, rhythmic flow influenced by classical Jagannath culture and coastal folk dialect.",
        sampleDialectSentence = "କଣ ହଉଛି ଭାଇ? ଜଗନ୍ନାଥ ମହାପ୍ରଭୁଙ୍କ କୃପାରୁ ସବୁ ଭଲ ତ?",
        phoneticCharacteristics = "Slightly elongated vowel cadences, expressive tonal rises on sentence endings."
    ),
    SAMBALPURI(
        title = "Sambalpuri-influenced Style",
        odiaLabel = "ସମ୍ବଲପୁରୀ ପ୍ରଭାବିତ ଶୈଳୀ",
        region = "Western Odisha (Sambalpur, Bargarh, Balangir, Sonepur)",
        description = "Energetic, rhythmic western Odisha cadence with distinct colloquial phonetics and vibrant intonation.",
        sampleDialectSentence = "ଜୁହାର! କେନ୍ତା ଅଛନ୍ ହୋ? ଆମର୍ ପଶ୍ଚିମ ଓଡ଼ିଶାର ଭାଷା ବହୁତ ମିଠା।",
        phoneticCharacteristics = "Sharper staccato rhythm, characteristic soft dental consonants, musical cadence."
    ),
    NORTHERN(
        title = "Northern Odisha Style",
        odiaLabel = "ଉତ୍ତର ଓଡ଼ିଶା ଶୈଳୀ",
        region = "Mayurbhanj, Balasore, Bhadrak",
        description = "Crisp, fast-flowing articulation with northern coastal nuances and distinctive tonal inflections.",
        sampleDialectSentence = "କିହୋ, କୁଆଡ଼େ ଚାଲିଛ? ବାଲେଶ୍ୱର ମୟୂରଭଞ୍ଜର କଥାବାର୍ତ୍ତା ଭାରି ନିଆରା।",
        phoneticCharacteristics = "Brisk syllable transitions, softer nasalization, higher clarity on conjunct characters."
    ),
    SOUTHERN(
        title = "Southern Odisha Style",
        odiaLabel = "ଦକ୍ଷିଣ ଓଡ଼ିଶା ଶୈଳୀ",
        region = "Ganjam, Berhampur, Gajapati, Koraput",
        description = "Vibrant, friendly, melodious Ganjami-influenced rhythm with warm conversational tone.",
        sampleDialectSentence = "ଆଜ୍ଞା ନମସ୍କାର! ବ୍ରହ୍ମପୁରୀୟା ଅନ୍ଦାଜ୍‌ରେ କଥାବାର୍ତ୍ତା କରିବାର ମଜା ହିଁ ଅଲଗା।",
        phoneticCharacteristics = "Warm rounded intonations, unique sentence closing lilt, friendly pitch contour."
    ),
    URBAN_MODERN(
        title = "Urban Modern Odia",
        odiaLabel = "ଆଧୁନିକ ସହରୀ ଓଡ଼ିଆ",
        region = "Bhubaneswar / Metro & Tech Hubs",
        description = "Contemporary, fluent urban speech style blending standard Odia with modern tech/media vocabulary.",
        sampleDialectSentence = "Welcome to modern AI voice studio! ଟେକ୍ନୋଲୋଜିର ନୂଆ ଅନୁଭୂତି ପାଇଁ ଆମେ ପ୍ରସ୍ତୁତ।",
        phoneticCharacteristics = "Smooth pacing, effortless switching with English technical loanwords, modern podcast feel."
    ),
    TRADITIONAL(
        title = "Traditional Odia Speaking Style",
        odiaLabel = "ପାରମ୍ପରିକ ଶାସ୍ତ୍ରୀୟ ଶୈଳୀ",
        region = "Classical & Heritage Odisha",
        description = "Poetic, deep, classical style reminiscent of ancient Kavya, Puranas, and historical narration.",
        sampleDialectSentence = "ହେ ମାନବ, ସତ୍ୟ, ଧର୍ମ ଓ ଶାନ୍ତିର ମାର୍ଗ ହିଁ ଚିରନ୍ତନ ମୁକ୍ତିର ସୋପାନ।",
        phoneticCharacteristics = "Deeper resonance, dignified slow tempo, full articulation of Sanskritized Odia tatshama words."
    );

    val displayName: String get() = title
    val nativeName: String get() = odiaLabel
    val sampleDialogue: String get() = sampleDialectSentence

    companion object {
        val DEFAULT = STANDARD
    }
}
