package com.pawelbryniarski.voicecalculator.presentation

import android.os.Bundle
import android.speech.SpeechRecognizer

/**
 * Created by pawelbryniarski on 28.06.2017.
 */

fun Bundle.getSpeech(): String {
    val data = getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
    val confidence: FloatArray = getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)
    val maxConfidenceIndex = confidence.indexOfMax()
    return data[maxConfidenceIndex]
}

private fun FloatArray.indexOfMax(): Int = this.indices.maxBy { this[it] } ?: -1
