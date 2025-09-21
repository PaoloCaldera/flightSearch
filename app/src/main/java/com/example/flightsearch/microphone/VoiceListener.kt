package com.example.flightsearch.microphone

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import com.example.flightsearch.microphone.model.VoiceState
import com.example.flightsearch.microphone.model.MicState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class VoiceListener(val voiceState: MutableStateFlow<VoiceState>) : RecognitionListener {
    override fun onReadyForSpeech(params: Bundle?) {
        voiceState.update {
            VoiceState(state = MicState.READY_FOR_SPEECH, result = "Try to say something")
        }
    }

    override fun onBeginningOfSpeech() {
        voiceState.update {
            VoiceState(state = MicState.BEGINNING_OF_SPEECH, result = "•••")
        }
    }

    override fun onPartialResults(partialResults: Bundle?) {
        partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { spokenText ->
                voiceState.update { VoiceState(state = MicState.SPEAKING, result = spokenText) }
            }
    }

    override fun onEndOfSpeech() {
        voiceState.update { VoiceState(state = MicState.END_OF_SPEECH) }
    }

    override fun onResults(results: Bundle?) {
        results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            ?.getOrNull(0)
            ?.let { spokenText ->
                voiceState.update { VoiceState(state = MicState.RESULT, result = spokenText) }
            }
    }

    override fun onError(error: Int) {
        if (error == SpeechRecognizer.ERROR_CLIENT)
            return
        else
            voiceState.update { VoiceState(state = MicState.ERROR, error = "Error: $error") }
    }

    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onEvent(eventType: Int, params: Bundle?) {}
    override fun onRmsChanged(rmsdB: Float) {}
}