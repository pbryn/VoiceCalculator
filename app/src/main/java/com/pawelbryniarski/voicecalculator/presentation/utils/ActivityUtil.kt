package com.pawelbryniarski.voicecalculator.presentation.utils

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by pawelbryniarski on 28.06.2017.
 */

fun <T : View> AppCompatActivity.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy { findViewById(res) as T }
}