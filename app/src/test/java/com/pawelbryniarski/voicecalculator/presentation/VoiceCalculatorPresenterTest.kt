@file:Suppress("IllegalIdentifier")

package com.pawelbryniarski.voicecalculator.presentation

import android.os.Bundle
import android.speech.SpeechRecognizer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.pawelbryniarski.voicecalculator.calculation.Calculator
import com.pawelbryniarski.voicecalculator.speechparser.SpeechParser
import org.junit.Before
import org.junit.Test

/**
 * Created by pawelbryniarski on 29.06.2017.
 */

class VoiceCalculatorPresenterTest {

    val calculatorMock: Calculator = mock()
    val speechParserMock: SpeechParser = mock()
    val viewMock: CalculatorView = mock()
    val speechBundleMock: Bundle = mock()
    val confidence: FloatArray = floatArrayOf(0.1f, 0.9f, 0.5f)
    val speech: ArrayList<String> = arrayListOf("dwa + oś", "2 plus osiem", "wa us jem")

    val tested = VoiceCalculatorPresenter(calculatorMock, speechParserMock, viewMock)

    @Before
    fun setUp() {
            whenever(speechBundleMock.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES)).thenReturn(confidence)
            whenever(speechBundleMock.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)).thenReturn(speech)
    }

    @Test
    fun `clears speech text when new speech provided`() {
        tested.onSpeech(speechBundleMock)

        verify(viewMock).showSpeech("")
    }

    @Test
    fun `sets speech textView to received speech`() {
        tested.onSpeech(speechBundleMock)

        verify(viewMock).showSpeech("2 plus osiem")
    }

    @Test
    fun `picks speech with the highest confidence`() {
        tested.onSpeech(speechBundleMock)

        verify(speechParserMock).parse("2 plus osiem")
    }

    @Test
    fun `shows correct calculation result`() {
        whenever(calculatorMock.calculate(any())).thenReturn(5)
        tested.onSpeech(speechBundleMock)

        verify(viewMock).showCalculationResult("5")

    }

    @Test
    fun `shows error when calculations fail`() {
        whenever(calculatorMock.calculate(any())).thenThrow(RuntimeException())

        tested.onSpeech(speechBundleMock)

        verify(viewMock).showError()
    }

    @Test
    fun `shows error when parsing fail`() {
        whenever(speechParserMock.parse(any())).thenThrow(RuntimeException())

        tested.onSpeech(speechBundleMock)

        verify(viewMock).showError()
    }
}