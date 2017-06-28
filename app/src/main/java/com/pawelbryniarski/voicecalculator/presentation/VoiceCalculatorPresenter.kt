package com.pawelbryniarski.voicecalculator.presentation

import android.os.Bundle
import com.pawelbryniarski.voicecalculator.calculation.Calculator
import com.pawelbryniarski.voicecalculator.texttransformation.PolishWordsToMath
import javax.inject.Inject

/**
 * Created by pawelbryniarski on 28.06.2017.
 */
class VoiceCalculatorPresenter
@Inject constructor(private val calculator: Calculator,
                    private val wordsToMath: PolishWordsToMath,
                    private val view: CalculatorView) {

    fun onSpeech(speechData: Bundle) {
        view.showSpeech("")
        val speech = speechData.getSpeech()
        view.showSpeech(speech)
        try {
            val result = calculator.calculate(wordsToMath.transform(speech))
            view.showCalculationResult(result.toString())
        } catch (e: Exception) {
            view.showError()
        }
    }
}