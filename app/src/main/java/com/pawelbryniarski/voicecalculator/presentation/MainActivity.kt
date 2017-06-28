package com.pawelbryniarski.voicecalculator.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.pawelbryniarski.voicecalculator.R
import javax.inject.Inject

class MainActivity : AppCompatActivity(), CalculatorView, TextToSpeech.OnInitListener {

    private companion object {
        val TAG = MainActivity::class.java.name.substring(0, 12)
    }

    private val speechText: TextView by bind(R.id.expression)
    @Inject
    lateinit var presenter: VoiceCalculatorPresenter
    @Inject
    lateinit var speechRecognizer: SpeechRecognizer
    @Inject
    lateinit var tts: TextToSpeech
    @Inject
    lateinit var locale: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(p0: Bundle?) {
                Log.d(TAG, "onReadyForSpeech")
            }

            override fun onRmsChanged(p0: Float) {
                Log.d(TAG, "onRmsChanged")
            }

            override fun onBufferReceived(p0: ByteArray?) {
                Log.d(TAG, "onBufferReceived")
            }

            override fun onPartialResults(p0: Bundle?) {
                Log.d(TAG, "onPartialResults")
            }

            override fun onEvent(p0: Int, p1: Bundle?) {
                Log.d(TAG, "onEvent $p0")
            }

            override fun onBeginningOfSpeech() {
                Log.d(TAG, "onBeginningOfSpeech")
            }

            override fun onEndOfSpeech() {
                Log.d(TAG, "onEndOfSpeech")
            }

            override fun onError(p0: Int) {
                Log.d(TAG, "onError $p0")
            }

            override fun onResults(results: Bundle) {
                presenter.onSpeech(results)
            }
        })
        findViewById(R.id.button).setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
            speechRecognizer.startListening(intent)
        }
    }

    private fun injectDependencies() {
        DaggerVoicCalculatorComponent
                .builder()
                .voiceRecognitionModule(VoiceRecognitionModule(this))
                .build()
                .inject(this)
    }

    override fun showSpeech(speech: String) {
        speechText.text = speech
    }

    override fun onInit(p0: Int) {
        Log.d("init", "init")
    }

    override fun showCalculationResult(result: String) = tts.speak(result)

    override fun showError() = tts.speak(getString(R.string.calculation_error))
}

private fun TextToSpeech.speak(text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        speak(text, TextToSpeech.QUEUE_FLUSH, null, "id")
    } else {
        @Suppress("DEPRECATION")
        speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }
}