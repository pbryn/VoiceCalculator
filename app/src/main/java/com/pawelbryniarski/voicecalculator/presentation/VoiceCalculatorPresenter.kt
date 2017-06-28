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

        val speech = speechData.getSpeech()
        view.showSpeech(speech)
        try {
            val result = calculator.calculate(wordsToMath.transform(speech))
            val resultText = if (result == 1) "jeden" else result.toString()
            view.showCalculationResult(resultText)
        } catch (e: Exception) {
            view.showError()
        }
    }
}