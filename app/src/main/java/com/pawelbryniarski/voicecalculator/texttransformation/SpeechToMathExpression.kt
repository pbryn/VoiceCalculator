package com.pawelbryniarski.voicecalculator.texttransformation

interface SpeechToMathExpression {
    fun transform(words: String): List<MathItem>
}