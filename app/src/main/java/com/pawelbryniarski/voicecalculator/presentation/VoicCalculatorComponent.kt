package com.pawelbryniarski.voicecalculator.presentation

import dagger.Component

/**
 * Created by pawelbryniarski on 28.06.2017.
 */
@Component(modules = arrayOf(VoiceRecognitionModule::class))
interface VoicCalculatorComponent {
    fun inject(activity: MainActivity)
}