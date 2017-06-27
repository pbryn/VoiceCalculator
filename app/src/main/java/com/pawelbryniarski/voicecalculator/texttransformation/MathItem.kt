package com.pawelbryniarski.voicecalculator.texttransformation

sealed class MathItem {
    data class Number(val value: Int) : MathItem()
    data class Operation(val value: String) : MathItem() {
        fun calculate(first: Int, second :Int) : Int =
            when(value){
                "+" -> first + second
                "-" -> first - second
                else -> throw  IllegalArgumentException()
            }

    }
}