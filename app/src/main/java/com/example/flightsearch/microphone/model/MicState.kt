package com.example.flightsearch.microphone.model

enum class MicState {
    READY_FOR_SPEECH,
    BEGINNING_OF_SPEECH,
    SPEAKING,
    END_OF_SPEECH,
    RESULT,
    OFF,
    ERROR
}