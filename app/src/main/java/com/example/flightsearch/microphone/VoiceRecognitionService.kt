package com.example.flightsearch.microphone

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.example.flightsearch.microphone.model.VoiceState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VoiceRecognitionService : Service() {
    private lateinit var speechRecognizer: SpeechRecognizer

    private val _voiceState = MutableStateFlow(VoiceState())
    val voiceState: StateFlow<VoiceState> = _voiceState.asStateFlow()

    override fun onCreate() {
        super.onCreate()
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this).apply {
            setRecognitionListener(VoiceListener(_voiceState))
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (!SpeechRecognizer.isRecognitionAvailable(this)) {
            _voiceState.update {
                it.copy(error = "Recognition is not available")
            }
        }
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
        speechRecognizer.startListening(intent)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return VoiceRecognitionServiceBinder(this)
    }

    override fun onDestroy() {
        _voiceState.update { VoiceState() }
        speechRecognizer.apply {
            stopListening()
            destroy()
        }
        super.onDestroy()
    }

    class VoiceRecognitionServiceBinder(val service: VoiceRecognitionService) : Binder()
}