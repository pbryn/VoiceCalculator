package com.pawelbryniarski.voicecalculator.speechparser

interface SpeechParser {
    fun parse(text: String): List<MathItem>
}