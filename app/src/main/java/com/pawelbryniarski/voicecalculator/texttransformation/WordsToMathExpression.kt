package com.pawelbryniarski.voicecalculator.texttransformation

interface WordsToMathExpression {
    fun transform(words: String): List<MathItem>
}