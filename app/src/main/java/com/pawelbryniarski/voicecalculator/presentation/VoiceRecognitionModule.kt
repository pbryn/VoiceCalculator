package com.pawelbryniarski.voicecalculator.presentation

import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import dagger.Module
import dagger.Provides
import java.util.*

@Module
class VoiceRecognitionModule(private val mainActivity: MainActivity) {

    @Provides
    fun speechRecognizer(): SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mainActivity)

    @Provides
    fun voiceCalculatorView(): CalculatorView = mainActivity

    @Provides
    fun locale(): String = "pl"

    @Provides
    fun textToSpeech(locale: String): TextToSpeech {
        val tts = TextToSpeech(mainActivity, null)
        tts.language = Locale(locale)
        return tts
    }
}