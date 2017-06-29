package com.pawelbryniarski.voicecalculator.calculation

import com.pawelbryniarski.voicecalculator.speechparser.MathItem

/**
 * Created by pawelbryniarski on 29.06.2017.
 */
interface Calculator{
    fun calculate(expression: List<MathItem>): Int
}