package com.example.service

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.content.FileProvider
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

data class PlayerState(
    val isPlaying: Boolean = false,
    val currentPositionMs: Long = 0L,
    val durationMs: Long = 0L,
    val currentFile: File? = null,
    val playbackSpeed: Float = 1.0f
)

class AudioPlayerManager(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var progressJob: Job? = null

    private val _playerState = MutableStateFlow(PlayerState())
    val playerState: StateFlow<PlayerState> = _playerState.asStateFlow()

    fun loadAndPlay(file: File) {
        if (!file.exists()) return

        stop()

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                val dur = duration.toLong()
                _playerState.value = _playerState.value.copy(
                    currentFile = file,
                    durationMs = dur,
                    currentPositionMs = 0L,
                    isPlaying = true
                )
                start()
                setOnCompletionListener {
                    _playerState.value = _playerState.value.copy(
                        isPlaying = false,
                        currentPositionMs = duration.toLong()
                    )
                    progressJob?.cancel()
                }
            }
            startProgressTracker()
        } catch (e: Exception) {
            Log.e("AudioPlayerManager", "Error playing audio file", e)
        }
    }

    fun togglePlayPause() {
        val player = mediaPlayer ?: return
        if (player.isPlaying) {
            player.pause()
            _playerState.value = _playerState.value.copy(isPlaying = false)
            progressJob?.cancel()
        } else {
            player.start()
            _playerState.value = _playerState.value.copy(isPlaying = true)
            startProgressTracker()
        }
    }

    fun seekTo(positionMs: Long) {
        mediaPlayer?.let { player ->
            val safePos = positionMs.coerceIn(0L, player.duration.toLong())
            player.seekTo(safePos.toInt())
            _playerState.value = _playerState.value.copy(currentPositionMs = safePos)
        }
    }

    fun skipForward(millis: Long = 10000L) {
        val current = _playerState.value.currentPositionMs
        seekTo(current + millis)
    }

    fun skipBackward(millis: Long = 10000L) {
        val current = _playerState.value.currentPositionMs
        seekTo(current - millis)
    }

    fun setPlaybackSpeed(speed: Float) {
        mediaPlayer?.let { player ->
            try {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    player.playbackParams = player.playbackParams.setSpeed(speed)
                    _playerState.value = _playerState.value.copy(playbackSpeed = speed)
                }
            } catch (e: Exception) {
                Log.e("AudioPlayerManager", "Error setting playback speed", e)
            }
        }
    }

    fun stop() {
        progressJob?.cancel()
        mediaPlayer?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        mediaPlayer = null
        _playerState.value = _playerState.value.copy(
            isPlaying = false,
            currentPositionMs = 0L
        )
    }

    private fun startProgressTracker() {
        progressJob?.cancel()
        progressJob = scope.launch {
            while (isActive) {
                mediaPlayer?.let { player ->
                    if (player.isPlaying) {
                        _playerState.value = _playerState.value.copy(
                            currentPositionMs = player.currentPosition.toLong(),
                            durationMs = player.duration.toLong()
                        )
                    }
                }
                delay(80)
            }
        }
    }

    fun shareAudio(file: File, title: String = "Bhasha Amar Audio") {
        try {
            val uri: Uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "audio/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_SUBJECT, title)
                putExtra(Intent.EXTRA_TEXT, "Generated with भाषा अमर | Bhasha Amar AI Voice Studio - Founded by Indrajit Hota")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            val chooser = Intent.createChooser(shareIntent, "Share Voice Audio").apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(chooser)
        } catch (e: Exception) {
            Log.e("AudioPlayerManager", "Error sharing audio", e)
            Toast.makeText(context, "Error sharing audio: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun exportAudio(file: File, exportName: String): File? {
        return try {
            val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadDir.exists()) downloadDir.mkdirs()
            val cleanName = exportName.replace(Regex("[^a-zA-Z0-9_-]"), "_")
            val destFile = File(downloadDir, "BhashaAmar_${cleanName}_${System.currentTimeMillis()}.wav")

            FileInputStream(file).use { input ->
                FileOutputStream(destFile).use { output ->
                    input.copyTo(output)
                }
            }
            Toast.makeText(context, "Exported successfully to Downloads!", Toast.LENGTH_LONG).show()
            destFile
        } catch (e: Exception) {
            Log.e("AudioPlayerManager", "Error exporting audio to downloads", e)
            Toast.makeText(context, "Exported to app storage: ${file.name}", Toast.LENGTH_SHORT).show()
            file
        }
    }

    fun release() {
        stop()
        scope.cancel()
    }
}
