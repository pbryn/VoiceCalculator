package com.pawelbryniarski.voicecalculator.presentation.di

import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import com.pawelbryniarski.voicecalculator.calculation.Calculator
import com.pawelbryniarski.voicecalculator.calculation.SimpleCalculator
import com.pawelbryniarski.voicecalculator.presentation.CalculatorView
import com.pawelbryniarski.voicecalculator.presentation.MainActivity
import com.pawelbryniarski.voicecalculator.speechparser.SpeechParser
import com.pawelbryniarski.voicecalculator.speechparser.polish.PolishSpeechParser
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
    fun calculator() : Calculator = SimpleCalculator()

    @Provides
    fun speechParser() : SpeechParser = PolishSpeechParser()

    @Provides
    fun textToSpeech(locale: String): TextToSpeech {
        val tts = TextToSpeech(mainActivity, null)
        tts.language = Locale(locale)
        return tts
    }
}