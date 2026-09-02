package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.ProjectEntity
import com.example.data.model.*
import com.example.data.repository.VoiceCatalog
import com.example.service.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.File

class VoiceStudioViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val projectDao = db.projectDao()

    val audioSynthesisEngine = AudioSynthesisEngine(application)
    val audioPlayerManager = AudioPlayerManager(application)

    // Current State
    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    private val _selectedLanguage = MutableStateFlow(SupportedLanguage.ODIA)
    val selectedLanguage: StateFlow<SupportedLanguage> = _selectedLanguage.asStateFlow()

    private val _isAutoDetect = MutableStateFlow(true)
    val isAutoDetect: StateFlow<Boolean> = _isAutoDetect.asStateFlow()

    private val _detectionResult = MutableStateFlow<LanguageDetectionResult?>(null)
    val detectionResult: StateFlow<LanguageDetectionResult?> = _detectionResult.asStateFlow()

    private val _selectedVoice = MutableStateFlow(VoiceCatalog.allVoices.first { it.id == "or_male_arjun" })
    val selectedVoice: StateFlow<Voice> = _selectedVoice.asStateFlow()

    private val _selectedEmotion = MutableStateFlow(Emotion.NEUTRAL)
    val selectedEmotion: StateFlow<Emotion> = _selectedEmotion.asStateFlow()

    private val _selectedSpeakingStyle = MutableStateFlow(SpeakingStyle.NORMAL_CONVERSATION)
    val selectedSpeakingStyle: StateFlow<SpeakingStyle> = _selectedSpeakingStyle.asStateFlow()

    private val _selectedOdiaAccent = MutableStateFlow(OdiaAccent.STANDARD)
    val selectedOdiaAccent: StateFlow<OdiaAccent> = _selectedOdiaAccent.asStateFlow()

    private val _speedRate = MutableStateFlow(1.0f)
    val speedRate: StateFlow<Float> = _speedRate.asStateFlow()

    private val _pitch = MutableStateFlow(1.0f)
    val pitch: StateFlow<Float> = _pitch.asStateFlow()

    private val _energy = MutableStateFlow("Medium")
    val energy: StateFlow<String> = _energy.asStateFlow()

    private val _customPronunciations = MutableStateFlow<List<PronunciationRule>>(emptyList())
    val customPronunciations: StateFlow<List<PronunciationRule>> = _customPronunciations.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    private val _generatedResult = MutableStateFlow<AudioGenerationResult?>(null)
    val generatedResult: StateFlow<AudioGenerationResult?> = _generatedResult.asStateFlow()

    private val _previewingVoiceId = MutableStateFlow<String?>(null)
    val previewingVoiceId: StateFlow<String?> = _previewingVoiceId.asStateFlow()

    // Voices Filter State
    private val _voiceSearchQuery = MutableStateFlow("")
    val voiceSearchQuery: StateFlow<String> = _voiceSearchQuery.asStateFlow()

    private val _voiceLanguageFilter = MutableStateFlow<SupportedLanguage?>(null)
    val voiceLanguageFilter: StateFlow<SupportedLanguage?> = _voiceLanguageFilter.asStateFlow()

    private val _voiceGenderFilter = MutableStateFlow<Gender?>(null)
    val voiceGenderFilter: StateFlow<Gender?> = _voiceGenderFilter.asStateFlow()

    private val _voiceAgeFilter = MutableStateFlow<AgeGroup?>(null)
    val voiceAgeFilter: StateFlow<AgeGroup?> = _voiceAgeFilter.asStateFlow()

    // Projects list from Room
    val projectsList: StateFlow<List<ProjectEntity>> = projectDao.getAllProjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Filtered Voices list
    val filteredVoices: StateFlow<List<Voice>> = combine(
        _voiceSearchQuery,
        _voiceLanguageFilter,
        _voiceGenderFilter,
        _voiceAgeFilter
    ) { query, lang, gender, age ->
        VoiceCatalog.allVoices.filter { voice ->
            val matchesQuery = query.isBlank() ||
                    voice.name.contains(query, ignoreCase = true) ||
                    voice.description.contains(query, ignoreCase = true) ||
                    voice.accentOrVariant.contains(query, ignoreCase = true)

            val matchesLang = lang == null || voice.language == lang
            val matchesGender = gender == null || voice.gender == gender
            val matchesAge = age == null || voice.ageGroup == age

            matchesQuery && matchesLang && matchesGender && matchesAge
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), VoiceCatalog.allVoices)

    init {
        // Initial sample text in Odia
        _inputText.value = SupportedLanguage.ODIA.sampleSentences.first()
    }

    fun onInputTextChanged(newText: String) {
        _inputText.value = newText
        if (_isAutoDetect.value && newText.isNotBlank()) {
            val result = LanguageDetector.detect(newText)
            _detectionResult.value = result
            if (result.detectedLanguage != _selectedLanguage.value) {
                _selectedLanguage.value = result.detectedLanguage
                // Update selected voice to the primary voice of that language if language changes
                val voices = VoiceCatalog.getVoicesForLanguage(result.detectedLanguage)
                if (voices.isNotEmpty()) {
                    _selectedVoice.value = voices.first()
                }
            }
        }
    }

    fun setAutoDetect(enabled: Boolean) {
        _isAutoDetect.value = enabled
        if (enabled && _inputText.value.isNotBlank()) {
            onInputTextChanged(_inputText.value)
        }
    }

    fun selectLanguage(language: SupportedLanguage) {
        _selectedLanguage.value = language
        _isAutoDetect.value = false
        val voices = VoiceCatalog.getVoicesForLanguage(language)
        if (voices.isNotEmpty()) {
            _selectedVoice.value = voices.first()
        }
    }

    fun selectVoice(voice: Voice) {
        _selectedVoice.value = voice
        _selectedLanguage.value = voice.language
    }

    fun selectEmotion(emotion: Emotion) {
        _selectedEmotion.value = emotion
    }

    fun selectSpeakingStyle(style: SpeakingStyle) {
        _selectedSpeakingStyle.value = style
    }

    fun selectOdiaAccent(accent: OdiaAccent) {
        _selectedOdiaAccent.value = accent
    }

    fun setSpeed(speed: Float) {
        _speedRate.value = speed
    }

    fun setPitch(p: Float) {
        _pitch.value = p
    }

    fun setEnergy(e: String) {
        _energy.value = e
    }

    fun insertPauseTag(tag: String) {
        val current = _inputText.value
        _inputText.value = if (current.isEmpty()) tag else "$current $tag "
    }

    fun addPronunciationRule(word: String, replacement: String) {
        if (word.isNotBlank() && replacement.isNotBlank()) {
            _customPronunciations.value = _customPronunciations.value + PronunciationRule(
                originalWord = word.trim(),
                replacementWord = replacement.trim(),
                languageCode = _selectedLanguage.value.code
            )
        }
    }

    fun removePronunciationRule(rule: PronunciationRule) {
        _customPronunciations.value = _customPronunciations.value - rule
    }

    fun previewVoice(voice: Voice, customSample: String? = null) {
        _previewingVoiceId.value = voice.id
        val textToSpeak = customSample ?: voice.sampleText
        audioSynthesisEngine.playPreview(
            text = textToSpeak,
            voice = voice,
            emotion = _selectedEmotion.value,
            style = _selectedSpeakingStyle.value,
            onDone = {
                _previewingVoiceId.value = null
            }
        )
    }

    fun stopPreview() {
        audioSynthesisEngine.stopSpeaking()
        _previewingVoiceId.value = null
    }

    fun generateVoice(onComplete: (AudioGenerationResult) -> Unit = {}) {
        val text = _inputText.value.ifBlank { _selectedLanguage.value.sampleSentences.first() }
        _isGenerating.value = true

        viewModelScope.launch {
            try {
                val result = audioSynthesisEngine.generateVoiceAudio(
                    text = text,
                    voice = _selectedVoice.value,
                    emotion = _selectedEmotion.value,
                    style = _selectedSpeakingStyle.value,
                    accent = _selectedOdiaAccent.value,
                    speed = _speedRate.value,
                    pitch = _pitch.value,
                    energy = _energy.value,
                    customPronunciations = _customPronunciations.value
                )
                _generatedResult.value = result
                _isGenerating.value = false
                audioPlayerManager.loadAndPlay(result.file)
                onComplete(result)
            } catch (e: Exception) {
                _isGenerating.value = false
            }
        }
    }

    fun saveCurrentToProjects(customTitle: String? = null) {
        val result = _generatedResult.value ?: return
        val title = customTitle?.ifBlank { null }
            ?: "Speech - ${_selectedVoice.value.name.take(15)} (${_selectedLanguage.value.displayName})"

        viewModelScope.launch {
            val project = ProjectEntity(
                title = title,
                text = _inputText.value,
                languageCode = _selectedLanguage.value.code,
                voiceId = _selectedVoice.value.id,
                voiceName = _selectedVoice.value.name,
                voiceGender = _selectedVoice.value.gender.displayName,
                emotion = _selectedEmotion.value.displayName,
                style = _selectedSpeakingStyle.value.displayName,
                accent = if (_selectedLanguage.value == SupportedLanguage.ODIA) _selectedOdiaAccent.value.title else "Standard",
                speed = _speedRate.value,
                pitch = _pitch.value,
                energy = _energy.value,
                audioFilePath = result.file.absolutePath,
                durationMs = result.durationMs
            )
            projectDao.insertProject(project)
        }
    }

    fun deleteProject(project: ProjectEntity) {
        viewModelScope.launch {
            projectDao.deleteProject(project)
            val file = File(project.audioFilePath)
            if (file.exists()) file.delete()
        }
    }

    fun toggleFavoriteProject(project: ProjectEntity) {
        viewModelScope.launch {
            projectDao.toggleFavorite(project.id)
        }
    }

    fun loadProjectIntoStudio(project: ProjectEntity) {
        _inputText.value = project.text
        val lang = SupportedLanguage.fromCode(project.languageCode)
        _selectedLanguage.value = lang
        val voice = VoiceCatalog.allVoices.firstOrNull { it.id == project.voiceId }
            ?: VoiceCatalog.getVoicesForLanguage(lang).firstOrNull()
            ?: VoiceCatalog.allVoices.first()
        _selectedVoice.value = voice
        _selectedEmotion.value = Emotion.entries.firstOrNull { it.displayName == project.emotion } ?: Emotion.NEUTRAL
        _selectedSpeakingStyle.value = SpeakingStyle.entries.firstOrNull { it.displayName == project.style } ?: SpeakingStyle.NORMAL_CONVERSATION
        _speedRate.value = project.speed
        _pitch.value = project.pitch
        _energy.value = project.energy

        val file = File(project.audioFilePath)
        if (file.exists()) {
            audioPlayerManager.loadAndPlay(file)
        }
    }

    // Voice search and filtering
    fun setVoiceSearchQuery(q: String) { _voiceSearchQuery.value = q }
    fun setVoiceLanguageFilter(lang: SupportedLanguage?) { _voiceLanguageFilter.value = lang }
    fun setVoiceGenderFilter(gender: Gender?) { _voiceGenderFilter.value = gender }
    fun setVoiceAgeFilter(age: AgeGroup?) { _voiceAgeFilter.value = age }

    override fun onCleared() {
        super.onCleared()
        audioSynthesisEngine.release()
        audioPlayerManager.release()
    }
}
