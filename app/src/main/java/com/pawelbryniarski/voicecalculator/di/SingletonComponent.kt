package com.pawelbryniarski.voicecalculator.di

import com.pawelbryniarski.voicecalculator.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(VoiceRecognitionModule::class))
interface SingletonComponent {
    fun inject(activity: MainActivity)
}

