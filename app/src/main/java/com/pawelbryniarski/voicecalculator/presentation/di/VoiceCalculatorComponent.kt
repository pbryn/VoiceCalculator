package com.pawelbryniarski.voicecalculator.presentation.di

import com.pawelbryniarski.voicecalculator.presentation.MainActivity
import dagger.Component

/**
 * Created by pawelbryniarski on 28.06.2017.
 */
@Component(modules = arrayOf(VoiceRecognitionModule::class))
interface VoiceCalculatorComponent {
    fun inject(activity: MainActivity)
}