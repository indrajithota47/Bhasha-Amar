package com.example.data.model

enum class SupportedLanguage(
    val code: String,
    val displayName: String,
    val nativeName: String,
    val scriptSample: String,
    val flagEmoji: String,
    val isFlagship: Boolean = false,
    val defaultPlaceholder: String,
    val sampleSentences: List<String>
) {
    ODIA(
        code = "or",
        displayName = "Odia",
        nativeName = "ଓଡ଼ିଆ",
        scriptSample = "ଓଡ଼ିଆ ଭାଷା",
        flagEmoji = "✨",
        isFlagship = true,
        defaultPlaceholder = "AI ଭଏସ୍ ଟେକ୍ନୋଲୋଜିର ନୂଆ ଦୁନିଆକୁ ଆପଣଙ୍କୁ ସ୍ୱାଗତ। ଏଠାରେ ଆପଣଙ୍କ ଟେକ୍ସଟ୍ ଲେଖନ୍ତୁ କିମ୍ବା ପେଷ୍ଟ କରନ୍ତୁ...",
        sampleSentences = listOf(
            "AI ଭଏସ୍ ଟେକ୍ନୋଲୋଜିର ନୂଆ ଦୁନିଆକୁ ଆପଣଙ୍କୁ ସ୍ୱାଗତ।",
            "ନମସ୍କାର, ମୁଁ ଆପଣଙ୍କ ପାଇଁ ଏକ ସ୍ୱାଭାବିକ AI ଭଏସ୍ ଡେମୋ ପ୍ରଦାନ କରୁଛି।",
            "ଓଡ଼ିଶାର କଳା, ସଂସ୍କୃତି ଓ ଭାଷା ଅତ୍ୟନ୍ତ ସମୃଦ୍ଧ ଏବଂ ଗୌରବମୟ।",
            "ଜୟ ଜଗନ୍ନାଥ! ଆପଣଙ୍କ ଦିନଟି ଶୁଭ ଏବଂ ଆନନ୍ଦମୟ ହେଉ।"
        )
    ),
    ENGLISH(
        code = "en",
        displayName = "English",
        nativeName = "English",
        scriptSample = "English Voice",
        flagEmoji = "🌐",
        isFlagship = false,
        defaultPlaceholder = "Welcome to the future of AI voice technology. Type or paste your text here...",
        sampleSentences = listOf(
            "Welcome to the future of AI voice technology.",
            "Hello, this is a natural AI voice demonstration.",
            "Transform your creative ideas into studio quality human-like speech.",
            "Experience expressive multilingual voice synthesis across styles."
        )
    ),
    HINDI(
        code = "hi",
        displayName = "Hindi",
        nativeName = "हिन्दी",
        scriptSample = "हिन्दी आवाज़",
        flagEmoji = "🇮🇳",
        isFlagship = false,
        defaultPlaceholder = "एआई वॉइस टेक्नोलॉजी की नई दुनिया में आपका स्वागत है। अपना टेक्स्ट यहाँ टाइप या पेस्ट करें...",
        sampleSentences = listOf(
            "एआई वॉइस टेक्नोलॉजी की नई दुनिया में आपका स्वागत है।",
            "नमस्ते, यह एक प्राकृतिक एआई आवाज़ का उदाहरण है।",
            "अपनी कहानियों और पॉडकास्ट को दें एक बेहतरीन और सजीव आवाज़।",
            "आर्टिफिशियल इंटेलिजेंस की मदद से हर शब्द को प्रभावशाली बनाएं।"
        )
    ),
    BENGALI(
        code = "bn",
        displayName = "Bengali",
        nativeName = "বাংলা",
        scriptSample = "বাংলা ভয়েস",
        flagEmoji = "🇧🇩",
        isFlagship = false,
        defaultPlaceholder = "এআই ভয়েস প্রযুক্তির নতুন জগতে আপনাকে স্বাগতম। আপনার টেক্সট এখানে লিখুন...",
        sampleSentences = listOf(
            "এআই ভয়েস প্রযুক্তির নতুন জগতে আপনাকে স্বাগতম।",
            "নমস্কার, এটি একটি প্রাকৃতিক এআই ভয়েসের উদাহরণ।",
            "আপনার সৃজনশীল লেখাকে দিন সুন্দর ও স্বাভাবিক কণ্ঠস্বর।",
            "আধুনিক এআই ভয়েস স্টুডিওর সাথে তৈরি করুন দারুণ সব অডিও।"
        )
    );

    companion object {
        fun fromCode(code: String): SupportedLanguage {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: ODIA
        }
    }
}
