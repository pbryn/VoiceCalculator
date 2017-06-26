package com.pawelbryniarski.voicecalculator

import android.app.Application
import android.content.Context
import com.pawelbryniarski.voicecalculator.di.DaggerSingletonComponent
import com.pawelbryniarski.voicecalculator.di.SingletonComponent
import com.pawelbryniarski.voicecalculator.di.VoiceRecognitionModule

class App : Application() {

    companion object {
        fun getApp(context: Context) = context.applicationContext as App
    }

    lateinit var singletonComponent: SingletonComponent
        private set

    override fun onCreate() {
        super.onCreate()
        singletonComponent = DaggerSingletonComponent.builder().voiceRecognitionModule(
                VoiceRecognitionModule(this)).build()
    }


}

