package com.example.flightsearch.microphone.model

data class VoiceState(
    val result: String = "",
    val state: MicState = MicState.OFF,
    val error: String? = null
)