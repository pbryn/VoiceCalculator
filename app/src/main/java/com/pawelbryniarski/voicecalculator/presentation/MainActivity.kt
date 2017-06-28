package com.pawelbryniarski.voicecalculator.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.pawelbryniarski.voicecalculator.R
import javax.inject.Inject


class MainActivity : AppCompatActivity(), CalculatorView {

    private companion object {
        val TAG = MainActivity::class.java.name.substring(0, 12)
        val RECORD_AUDIO_REQUEST_CODE = 123
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
                tts.speak(getString(R.string.unable_to_recognize_speech))
            }

            override fun onResults(results: Bundle) {
                presenter.onSpeech(results)
            }
        })

        findViewById(R.id.button).setOnClickListener {
            val permissionCheck = ContextCompat.checkSelfPermission(MainActivity@ this,
                    Manifest.permission.RECORD_AUDIO)
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity@ this,
                        arrayOf(Manifest.permission.RECORD_AUDIO),
                        RECORD_AUDIO_REQUEST_CODE)
            } else {
                acceptExpressionToEvaluate()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == RECORD_AUDIO_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                acceptExpressionToEvaluate()
            }
        }
    }

    override fun showSpeech(speech: String) {
        speechText.text = speech
    }

    override fun showCalculationResult(result: String) = tts.speak(result)

    override fun showError() = tts.speak(getString(R.string.calculation_error))

    private fun injectDependencies() {
        DaggerVoicCalculatorComponent
                .builder()
                .voiceRecognitionModule(VoiceRecognitionModule(this))
                .build()
                .inject(this)
    }

    private fun acceptExpressionToEvaluate() {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
            putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
        }.run { speechRecognizer.startListening(this) }
    }

    private fun TextToSpeech.speak(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            speak(text, TextToSpeech.QUEUE_FLUSH, null, "id")
        } else {
            @Suppress("DEPRECATION")
            speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }
}

