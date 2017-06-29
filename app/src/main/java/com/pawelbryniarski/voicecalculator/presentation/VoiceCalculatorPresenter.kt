package com.pawelbryniarski.voicecalculator.presentation

import android.os.Bundle
import com.pawelbryniarski.voicecalculator.calculation.Calculator
import com.pawelbryniarski.voicecalculator.presentation.utils.getSpeech
import com.pawelbryniarski.voicecalculator.speechparser.SpeechParser
import javax.inject.Inject

/**
 * Created by pawelbryniarski on 28.06.2017.
 */
class VoiceCalculatorPresenter
@Inject constructor(private val calculator: Calculator,
                    private val speechParser: SpeechParser,
                    private val view: CalculatorView) {

    fun onSpeech(speechData: Bundle) {
        view.showSpeech("")
        val speech = speechData.getSpeech()
        view.showSpeech(speech)
        try {
            val result = calculator.calculate(speechParser.parse(speech))
            view.showCalculationResult(result.toString())
        } catch (e: Exception) {
            view.showError()
        }
    }
}