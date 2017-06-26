package com.pawelbryniarski.voicecalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.SpeechRecognizer
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var speechRecognizer : SpeechRecognizer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.getApp(this).singletonComponent.inject(this)
    }
}
