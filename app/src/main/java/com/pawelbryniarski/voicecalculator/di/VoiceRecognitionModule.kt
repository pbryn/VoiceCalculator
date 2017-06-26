package com.pawelbryniarski.voicecalculator.di

import android.content.Context
import android.speech.SpeechRecognizer
import dagger.Module
import dagger.Provides

@dagger.Module
class VoiceRecognitionModule(private val context: android.content.Context) {

    @dagger.Provides
    fun speechRecognizer(): android.speech.SpeechRecognizer = android.speech.SpeechRecognizer.createSpeechRecognizer(context)
}