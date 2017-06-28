package com.pawelbryniarski.voicecalculator.texttransformation

sealed class MathItem {
    data class Number(val value: Int) : MathItem()
    data class Operation(val value: String) : MathItem()
}