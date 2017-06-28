package com.pawelbryniarski.voicecalculator.presentation

interface CalculatorView {
    fun showSpeech(speech: String)
    fun showCalculationResult(result : String)
    fun showError()
}