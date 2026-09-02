package com.example.data.repository

import com.example.data.model.*

object VoiceCatalog {

    val allVoices: List<Voice> = listOf(
        // ==========================================
        // ODIA VOICES (Flagship Priority)
        // ==========================================
        // Odia Male
        Voice(
            id = "or_male_arjun",
            name = "Arjun (ଅର୍ଜୁନ)",
            language = SupportedLanguage.ODIA,
            gender = Gender.MALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Standard Odia",
            style = VoiceStyle.PROFESSIONAL,
            description = "Crisp, dynamic youthful male voice with modern articulate Odia pronunciation.",
            pitchMultiplier = 1.05f,
            rateMultiplier = 1.0f,
            sampleText = "ନମସ୍କାର, ମୁଁ ଅର୍ଜୁନ। ଆପଣଙ୍କ ଟେକ୍ସଟ୍‌କୁ ଏକ ପ୍ରାକୃତିକ ଏବଂ ସ୍ପଷ୍ଟ ସ୍ୱରରେ ପରିଣତ କରିପାରିବି।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF4F46E5
        ),
        Voice(
            id = "or_male_soumya",
            name = "Soumya (ସୌମ୍ୟ)",
            language = SupportedLanguage.ODIA,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Coastal Odia Style",
            style = VoiceStyle.CASUAL,
            description = "Warm, balanced adult voice with coastal Cuttack-Puri melodic inflection.",
            pitchMultiplier = 0.98f,
            rateMultiplier = 0.98f,
            sampleText = "ଜୟ ଜଗନ୍ନାଥ! ଉପକୂଳ ଓଡ଼ିଶାର ମଧୁର ଏବଂ ଆନ୍ତରିକ ସ୍ୱରରେ ସମସ୍ତ ବିଷୟ ଉପସ୍ଥାପନ କରିବା ମୋର ଲକ୍ଷ୍ୟ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF0284C7
        ),
        Voice(
            id = "or_male_biswajit",
            name = "Biswajit (ବିଶ୍ୱଜିତ୍)",
            language = SupportedLanguage.ODIA,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Standard Odia",
            style = VoiceStyle.DEEP,
            description = "Deep, rich baritone voice ideal for podcasts, news, and documentary narration.",
            pitchMultiplier = 0.85f,
            rateMultiplier = 0.95f,
            sampleText = "ଗମ୍ଭୀର ଏବଂ ପ୍ରଭାବଶାଳୀ ଉଚ୍ଚାରଣ ସହିତ ସମସ୍ତ ତଥ୍ୟକୁ ଶ୍ରୋତାଙ୍କ ନିକଟରେ ସ୍ପଷ୍ଟ ଭାବେ ପହଞ୍ଚାନ୍ତୁ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF1E293B
        ),
        Voice(
            id = "or_male_rabindra",
            name = "Rabindra (ରବୀନ୍ଦ୍ର)",
            language = SupportedLanguage.ODIA,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Traditional Odia Speaking Style",
            style = VoiceStyle.NARRATOR,
            description = "Captivating classical narrator voice with poetic cadence for audiobooks and history.",
            pitchMultiplier = 0.92f,
            rateMultiplier = 0.90f,
            sampleText = "ପ୍ରାଚୀନ ଉତ୍କଳର ମହାନ ଗାଥା ଓ ଇତିହାସକୁ ଜୀବନ୍ତ କରିବା ପାଇଁ ଏହି ସ୍ୱର ଅତ୍ୟନ୍ତ ଉପଯୋଗୀ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF7C3AED
        ),
        Voice(
            id = "or_male_sambalpur_keshav",
            name = "Keshav (କେଶବ - Sambalpuri)",
            language = SupportedLanguage.ODIA,
            gender = Gender.MALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Sambalpuri-influenced Style",
            style = VoiceStyle.ENERGETIC,
            description = "Lively Western Odisha Sambalpuri cadence with vibrant rhythm and authentic dialect tones.",
            pitchMultiplier = 1.02f,
            rateMultiplier = 1.05f,
            sampleText = "ଜୁହାର! ଆମର୍ ପଶ୍ଚିମ ଓଡ଼ିଶାର ମିଠା ସ୍ୱର୍‌ରେ ଆପଣଙ୍କ କଥା କହିବା ଲାଗି ମୁଁ ରେଡ଼ି ଅଛେଁ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFFD97706
        ),
        Voice(
            id = "or_male_dayanidhi",
            name = "Dayanidhi (ଦୟାନିଧି - Old Man)",
            language = SupportedLanguage.ODIA,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ELDERLY,
            accentOrVariant = "Traditional Odia Speaking Style",
            style = VoiceStyle.TRADITIONAL,
            description = "Wise, venerable elder male voice with calm breath pauses and grandfatherly warmth.",
            pitchMultiplier = 0.78f,
            rateMultiplier = 0.85f,
            sampleText = "ପୁଅ, ସତ୍ୟ ଓ ଧର୍ମର ବାଟରେ ଚାଲିଲେ ଜୀବନରେ କେବେହେଲେ ଅସୁବିଧା ହୁଏନାହିଁ। ଭଲରେ ରୁହ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF78350F
        ),
        Voice(
            id = "or_male_siddhartha",
            name = "Siddhartha (ସିଦ୍ଧାର୍ଥ - Urban)",
            language = SupportedLanguage.ODIA,
            gender = Gender.MALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Urban Modern Odia",
            style = VoiceStyle.PROFESSIONAL,
            description = "Smooth Bhubaneswar urban media voice tailored for tech videos, promos, and commercials.",
            pitchMultiplier = 1.00f,
            rateMultiplier = 1.04f,
            sampleText = "ଡିଜିଟାଲ୍ ଯୁଗରେ ଏଆଇ ଭଏସ୍ ସହିତ ଆପଣଙ୍କ ଭିଡ଼ିଓ ଏବଂ ପ୍ରୋଜେକ୍ଟକୁ କରନ୍ତୁ ଆହୁରି ଆକର୍ଷଣୀୟ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF059669
        ),

        // Odia Female
        Voice(
            id = "or_female_ananya",
            name = "Ananya (ଅନନ୍ୟା)",
            language = SupportedLanguage.ODIA,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Standard Odia",
            style = VoiceStyle.PROFESSIONAL,
            description = "Sweet, articulate young female voice with flawless Odia pronunciation and high clarity.",
            pitchMultiplier = 1.25f,
            rateMultiplier = 1.0f,
            sampleText = "ନମସ୍କାର! ମୁଁ ଅନନ୍ୟା। ଆପଣଙ୍କ ଭାଷା ଏବଂ ଭାବନାକୁ ସଠିକ୍ ଭାବରେ ପ୍ରକାଶ କରିବା ମୋର ପ୍ରୟାସ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFFEC4899
        ),
        Voice(
            id = "or_female_madhumita",
            name = "Madhumita (ମଧୁମିତା)",
            language = SupportedLanguage.ODIA,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Coastal Odia Style",
            style = VoiceStyle.SOFT,
            description = "Gentle, melodious, empathetic female voice perfect for storytelling, poetry, and meditation.",
            pitchMultiplier = 1.18f,
            rateMultiplier = 0.92f,
            sampleText = "ଶାନ୍ତ ଏବଂ ମଧୁର ସ୍ୱରରେ ଆପଣଙ୍କ କବିତା ଏବଂ କାହାଣୀ ଶ୍ରୋତାଙ୍କ ହୃଦୟକୁ ସ୍ପର୍ଶ କରିବ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFFF43F5E
        ),
        Voice(
            id = "or_female_suchitra",
            name = "Suchitra (ସୁଚିତ୍ରା)",
            language = SupportedLanguage.ODIA,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Standard Odia",
            style = VoiceStyle.NARRATOR,
            description = "Crisp, authoritative broadcast voice suitable for education, tutorials, and news reading.",
            pitchMultiplier = 1.15f,
            rateMultiplier = 0.98f,
            sampleText = "ଆଜିର ମୁଖ୍ୟ ଖବର ଏବଂ ଶିକ୍ଷଣୀୟ ବିଷୟବସ୍ତୁ ଉପରେ ଆସନ୍ତୁ ବିସ୍ତୃତ ଆଲୋଚନା କରିବା।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF8B5CF6
        ),
        Voice(
            id = "or_female_sampriti_sambalpur",
            name = "Sampriti (ସମ୍ପ୍ରୀତି - Sambalpuri)",
            language = SupportedLanguage.ODIA,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Sambalpuri-influenced Style",
            style = VoiceStyle.CASUAL,
            description = "Charming Western Odisha female voice with rhythmic folk nuances and expressive pitch.",
            pitchMultiplier = 1.28f,
            rateMultiplier = 1.05f,
            sampleText = "ନୂଆଁଖାଇ ଜୁହାର! ସମ୍ବଲପୁରୀ ମିଠା ଭାଷାରେ ଗପ ଶୁଣିବାକୁ ସମସ୍ତେ ଭଲ ପାଆନ୍ତି।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFFEA580C
        ),
        Voice(
            id = "or_female_annapurna",
            name = "Annapurna (ଅନ୍ନପୂର୍ଣ୍ଣା - Old Woman)",
            language = SupportedLanguage.ODIA,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.ELDERLY,
            accentOrVariant = "Traditional Odia Speaking Style",
            style = VoiceStyle.TRADITIONAL,
            description = "Warm grandmotherly voice with serene pacing, nostalgic warmth, and devotional charm.",
            pitchMultiplier = 1.02f,
            rateMultiplier = 0.82f,
            sampleText = "ହେ ଭଗବାନ, ସମସ୍ତଙ୍କର ମଙ୍ଗଳ କର। ଧର୍ମ ଏବଂ ସତ୍ୟର ପଥ ହିଁ ସର୍ବଦା ଆଲୋକମୟ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFFB45309
        ),
        Voice(
            id = "or_female_barsha_north",
            name = "Barsha (ବର୍ଷା - Northern)",
            language = SupportedLanguage.ODIA,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Northern Odisha Style",
            style = VoiceStyle.ENERGETIC,
            description = "Fast-flowing Mayurbhanj & Balasore accent with lively dynamic tonal cadence.",
            pitchMultiplier = 1.30f,
            rateMultiplier = 1.08f,
            sampleText = "ବାଲେଶ୍ୱର ଓ ମୟୂରଭଞ୍ଜର ସ୍ୱତନ୍ତ୍ର ଶୈଳୀରେ କଥା କହିବା ବହୁତ ରୋଚକ ଅଟେ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF06B6D4
        ),

        // Odia Child Voices
        Voice(
            id = "or_child_guddu",
            name = "Guddu (ଗୁଡ୍ଡୁ - Cute Boy)",
            language = SupportedLanguage.ODIA,
            gender = Gender.CHILD,
            ageGroup = AgeGroup.CHILD,
            accentOrVariant = "Standard Odia",
            style = VoiceStyle.CUTE,
            description = "Playful, adorable young boy voice for cartoons, moral stories, and kids content.",
            pitchMultiplier = 1.55f,
            rateMultiplier = 1.08f,
            sampleText = "ମୋତେ ମାମୁଁ ଘରକୁ ଯିବାକୁ ବହୁତ ଭଲ ଲାଗେ! ମୁଁ ସେଠାରେ ବହୁତ ମଜା କରେ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF38BDF8
        ),
        Voice(
            id = "or_child_guddi",
            name = "Guddi (ଗୁଡ୍ଡି - Cute Girl)",
            language = SupportedLanguage.ODIA,
            gender = Gender.CHILD,
            ageGroup = AgeGroup.CHILD,
            accentOrVariant = "Standard Odia",
            style = VoiceStyle.CUTE,
            description = "Sparkling, delightful young girl voice with curious, charming intonations.",
            pitchMultiplier = 1.62f,
            rateMultiplier = 1.10f,
            sampleText = "ଆଜି ମୁଁ ସ୍କୁଲରେ ଗୋଟିଏ ନୂଆ ଗୀତ ଶିଖିଛି, ଆସନ୍ତୁ ସମସ୍ତେ ଏକାଠି ଗାଇବା!",
            isFlagshipOdia = true,
            avatarColorHex = 0xFFF472B6
        ),
        Voice(
            id = "or_child_kanha_teen",
            name = "Kanha (କାହ୍ନା - Teen Boy)",
            language = SupportedLanguage.ODIA,
            gender = Gender.MALE,
            ageGroup = AgeGroup.TEEN,
            accentOrVariant = "Urban Modern Odia",
            style = VoiceStyle.CASUAL,
            description = "Energetic teenage boy voice with contemporary cadence and youthful enthusiasm.",
            pitchMultiplier = 1.18f,
            rateMultiplier = 1.06f,
            sampleText = "ହେଲୋ ବନ୍ଧୁଗଣ! ଚାଲନ୍ତୁ ଆଜି କିଛି ନୂଆ ଟେକ୍ ଏବଂ ଗେମିଂ କଥା ଜାଣିବା।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFF10B981
        ),
        Voice(
            id = "or_child_tulasi_teen",
            name = "Tulasi (ତୁଳସୀ - Storytelling Child)",
            language = SupportedLanguage.ODIA,
            gender = Gender.CHILD,
            ageGroup = AgeGroup.TEEN,
            accentOrVariant = "Standard Odia",
            style = VoiceStyle.STORYTELLING,
            description = "Articulate youth storytelling voice for fables, school recitations, and audio adventures.",
            pitchMultiplier = 1.38f,
            rateMultiplier = 0.95f,
            sampleText = "ଜଙ୍ଗଲ ଭିତରେ ଗୋଟିଏ ସୁନ୍ଦର ନଦୀ ବହୁଥିଲା, ଯେଉଁଠାରେ ସବୁ ପଶୁପକ୍ଷୀ ଖୁସିରେ ରହୁଥିଲେ।",
            isFlagshipOdia = true,
            avatarColorHex = 0xFFA855F7
        ),

        // ==========================================
        // ENGLISH VOICES
        // ==========================================
        Voice(
            id = "en_male_marcus",
            name = "Marcus",
            language = SupportedLanguage.ENGLISH,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Studio Neutral",
            style = VoiceStyle.PROFESSIONAL,
            description = "Crisp, authoritative studio voice tailored for product explainers and global tech podcasts.",
            pitchMultiplier = 0.95f,
            rateMultiplier = 1.0f,
            sampleText = "Welcome to Bhasha Amar, where every language and every emotion finds its natural voice.",
            avatarColorHex = 0xFF3B82F6
        ),
        Voice(
            id = "en_male_david",
            name = "David (Narrator)",
            language = SupportedLanguage.ENGLISH,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Deep Cinematic",
            style = VoiceStyle.DEEP,
            description = "Resonant baritone with cinematic gravitas for documentary narration and movie trailers.",
            pitchMultiplier = 0.82f,
            rateMultiplier = 0.92f,
            sampleText = "Across the expanse of time and culture, human speech remains our most powerful bond.",
            avatarColorHex = 0xFF1E3A8A
        ),
        Voice(
            id = "en_female_sarah",
            name = "Sarah",
            language = SupportedLanguage.ENGLISH,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Modern American",
            style = VoiceStyle.CASUAL,
            description = "Bright, modern, conversational female voice for social media, YouTube, and podcasts.",
            pitchMultiplier = 1.22f,
            rateMultiplier = 1.05f,
            sampleText = "Hey everyone! Experience seamless AI text-to-speech with instant emotional styling.",
            avatarColorHex = 0xFFDB2777
        ),
        Voice(
            id = "en_female_clara",
            name = "Clara (Professional)",
            language = SupportedLanguage.ENGLISH,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "British Broadcast",
            style = VoiceStyle.PROFESSIONAL,
            description = "Polished, elegant cadence with refined corporate clarity and instructional authority.",
            pitchMultiplier = 1.15f,
            rateMultiplier = 0.98f,
            sampleText = "Good day. Today we explore advanced neural voice models and multilingual synthesis.",
            avatarColorHex = 0xFF9333EA
        ),
        Voice(
            id = "en_female_emma",
            name = "Emma (Soft)",
            language = SupportedLanguage.ENGLISH,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Gentle Conversational",
            style = VoiceStyle.SOFT,
            description = "Calm, gentle whisper-soft delivery ideal for meditation, wellness, and bedside stories.",
            pitchMultiplier = 1.18f,
            rateMultiplier = 0.88f,
            sampleText = "Take a slow, deep breath, and let your mind settle into serene, quiet focus.",
            avatarColorHex = 0xFF0D9488
        ),
        Voice(
            id = "en_child_timmy",
            name = "Timmy (Cute Boy)",
            language = SupportedLanguage.ENGLISH,
            gender = Gender.CHILD,
            ageGroup = AgeGroup.CHILD,
            accentOrVariant = "Animated Young",
            style = VoiceStyle.CUTE,
            description = "Joyful, bouncy boy voice for children's animation, games, and learning stories.",
            pitchMultiplier = 1.58f,
            rateMultiplier = 1.08f,
            sampleText = "Look at the rocket ship zooming all the way up to the glowing stars!",
            avatarColorHex = 0xFF0284C7
        ),
        Voice(
            id = "en_child_lily",
            name = "Lily (Cute Girl)",
            language = SupportedLanguage.ENGLISH,
            gender = Gender.CHILD,
            ageGroup = AgeGroup.CHILD,
            accentOrVariant = "Sweet Youth",
            style = VoiceStyle.CUTE,
            description = "Inquisitive, cheerful girl voice with bright, joyful cadence.",
            pitchMultiplier = 1.65f,
            rateMultiplier = 1.05f,
            sampleText = "Can we read the fairy tale book together before going to sleep tonight?",
            avatarColorHex = 0xFFFB7185
        ),

        // ==========================================
        // HINDI VOICES (हिन्दी)
        // ==========================================
        Voice(
            id = "hi_male_aarav",
            name = "Aarav (आरव)",
            language = SupportedLanguage.HINDI,
            gender = Gender.MALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Standard Hindi",
            style = VoiceStyle.PROFESSIONAL,
            description = "Clear, articulate contemporary Hindi voice suitable for explainer videos and audio courses.",
            pitchMultiplier = 1.02f,
            rateMultiplier = 1.0f,
            sampleText = "नमस्ते, भाषा अमर में आपका स्वागत है। अपनी आवाज़ को दें एक नया और प्रभावशाली रूप।",
            avatarColorHex = 0xFF4338CA
        ),
        Voice(
            id = "hi_male_raghav",
            name = "Raghav (राघव)",
            language = SupportedLanguage.HINDI,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Deep Narrator",
            style = VoiceStyle.DEEP,
            description = "Deep, resonant Hindi narrator voice for historical documentaries, literature, and podcasts.",
            pitchMultiplier = 0.86f,
            rateMultiplier = 0.94f,
            sampleText = "इतिहास के पन्नों में छुपी अनकही कहानियों को जीवंत बनाने के लिए यह आवाज़ उपयुक्त है।",
            avatarColorHex = 0xFF1E293B
        ),
        Voice(
            id = "hi_male_premchand",
            name = "Premchand (प्रेमचंद - Elder)",
            language = SupportedLanguage.HINDI,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ELDERLY,
            accentOrVariant = "Traditional Hindi",
            style = VoiceStyle.TRADITIONAL,
            description = "Wise elder grandfather voice with classical Hindi diction and warm nostalgic cadence.",
            pitchMultiplier = 0.78f,
            rateMultiplier = 0.84f,
            sampleText = "बेटा, अनुभव और ज्ञान ही इंसान की सबसे बड़ी पूंजी हैं। सदा सच के रास्ते पर चलो।",
            avatarColorHex = 0xFF78350F
        ),
        Voice(
            id = "hi_female_ananya",
            name = "Ananya (अनन्या)",
            language = SupportedLanguage.HINDI,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Modern Hindi",
            style = VoiceStyle.CASUAL,
            description = "Pleasant, expressive young female voice for social media, storytelling, and audio drama.",
            pitchMultiplier = 1.24f,
            rateMultiplier = 1.02f,
            sampleText = "नमस्ते दोस्तों! आज हम बात करेंगे एआई की मदद से बेहतरीन वॉइसओवर तैयार करने के बारे में।",
            avatarColorHex = 0xFFBE185D
        ),
        Voice(
            id = "hi_female_priya",
            name = "Priya (प्रिया)",
            language = SupportedLanguage.HINDI,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Broadcast Hindi",
            style = VoiceStyle.PROFESSIONAL,
            description = "Refined, professional broadcaster voice for news, commercials, and corporate presentations.",
            pitchMultiplier = 1.16f,
            rateMultiplier = 0.98f,
            sampleText = "आज के मुख्य समाचार और तकनीकी विश्लेषण के इस विशेष बुलेटिन में आपका हार्दिक स्वागत है।",
            avatarColorHex = 0xFF6D28D9
        ),
        Voice(
            id = "hi_child_chhote",
            name = "Chhote (छोटे - Cute Boy)",
            language = SupportedLanguage.HINDI,
            gender = Gender.CHILD,
            ageGroup = AgeGroup.CHILD,
            accentOrVariant = "Standard Hindi",
            style = VoiceStyle.CUTE,
            description = "Playful, enthusiastic child voice for cartoon dubbing, rhymes, and kids stories.",
            pitchMultiplier = 1.56f,
            rateMultiplier = 1.08f,
            sampleText = "चंदा मामा दूर के, पुए पकाएं बूर के! मुझे यह कविता बहुत अच्छी लगती है।",
            avatarColorHex = 0xFF0284C7
        ),

        // ==========================================
        // BENGALI VOICES (বাংলা)
        // ==========================================
        Voice(
            id = "bn_male_sourav",
            name = "Sourav (সৌরভ)",
            language = SupportedLanguage.BENGALI,
            gender = Gender.MALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Standard Bengali",
            style = VoiceStyle.PROFESSIONAL,
            description = "Fluent, natural Kolkata Bengali voice with clear pronunciation and modern cadence.",
            pitchMultiplier = 1.02f,
            rateMultiplier = 1.0f,
            sampleText = "নমস্কার, ভাষা অমরে আপনাকে স্বাগতম। আপনার লেখাকে দিন এক সুন্দর ও জীবন্ত কণ্ঠস্বর।",
            avatarColorHex = 0xFF1D4ED8
        ),
        Voice(
            id = "bn_male_satyajit",
            name = "Satyajit (সত্যজিৎ - Deep)",
            language = SupportedLanguage.BENGALI,
            gender = Gender.MALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Classic Bengali Narrator",
            style = VoiceStyle.DEEP,
            description = "Deep baritone voice with rich cultural cadence for audiobooks, drama, and documentaries.",
            pitchMultiplier = 0.85f,
            rateMultiplier = 0.92f,
            sampleText = "বাংলার সমৃদ্ধ সাহিত্য ও সংস্কৃতির গল্পগুলোকে নতুন করে অনুভব করুন এই গভীর কণ্ঠে।",
            avatarColorHex = 0xFF334155
        ),
        Voice(
            id = "bn_female_shreya",
            name = "Shreya (শ্রেয়া)",
            language = SupportedLanguage.BENGALI,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.YOUNG,
            accentOrVariant = "Standard Bengali",
            style = VoiceStyle.PROFESSIONAL,
            description = "Sweet, articulate female voice with melodious intonation for audiobooks and media.",
            pitchMultiplier = 1.25f,
            rateMultiplier = 1.0f,
            sampleText = "নমস্কার! সহজ এবং স্বাভাবিক উচ্চারণে আপনার যেকোনো গল্পকে করে তুলুন আরও মনোগ্রাহী।",
            avatarColorHex = 0xFFC026D3
        ),
        Voice(
            id = "bn_female_aparna",
            name = "Aparna (অপর্ণা - Soft)",
            language = SupportedLanguage.BENGALI,
            gender = Gender.FEMALE,
            ageGroup = AgeGroup.ADULT,
            accentOrVariant = "Melodious Bengali",
            style = VoiceStyle.SOFT,
            description = "Gentle, emotional female voice for poetry recitation, bedtime stories, and warm narration.",
            pitchMultiplier = 1.18f,
            rateMultiplier = 0.90f,
            sampleText = "মেঘের দেশে একলা বসে আকাশ দেখি আমি, এমন স্নিগ্ধ অনুভূতি সত্যিই অনবদ্য।",
            avatarColorHex = 0xFFE11D48
        ),
        Voice(
            id = "bn_child_bubai",
            name = "Bubai (বুবাই - Cute Child)",
            language = SupportedLanguage.BENGALI,
            gender = Gender.CHILD,
            ageGroup = AgeGroup.CHILD,
            accentOrVariant = "Standard Bengali",
            style = VoiceStyle.CUTE,
            description = "Cheerful Bengali child voice with playful energy for cartoons and kids rhymes.",
            pitchMultiplier = 1.55f,
            rateMultiplier = 1.06f,
            sampleText = "আম পাতা জোড়া জোড়া, মারবো চাবুক চড়বো ঘোড়া! চলো সবাই মিলে খেলি!",
            avatarColorHex = 0xFF059669
        )
    )

    val odiaVoices: List<Voice> get() = allVoices.filter { it.language == SupportedLanguage.ODIA }

    fun getVoicesForLanguage(language: SupportedLanguage): List<Voice> {
        return allVoices.filter { it.language == language }
    }

    fun getVoiceById(id: String): Voice {
        return allVoices.firstOrNull { it.id == id } ?: allVoices.first()
    }
}
