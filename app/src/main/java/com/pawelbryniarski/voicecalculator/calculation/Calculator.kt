package com.pawelbryniarski.voicecalculator.calculation

import com.pawelbryniarski.voicecalculator.texttransformation.MathItem
import com.pawelbryniarski.voicecalculator.texttransformation.MathItem.Number
import com.pawelbryniarski.voicecalculator.texttransformation.MathItem.Operation
import java.util.LinkedList

class Calculator {
    private val numbersStack = LinkedList<Number>()
    private val operatorsStack = LinkedList<Operation>()

    fun calculate(expression: List<MathItem>): Int {
        val ex = LinkedList(expression)
        while (true) {
            val item = ex.pollFirst()
            if (item != null) {

                if (item is Operation) {
                    when (item.value) {
                        "*" -> {
                            val next = ex.pollFirst()
                            if (next is Number) {
                                val result = numbersStack.poll().value * next.value
                                numbersStack.push(Number(result))
                            } else {
                                // operation must by "-"
                                next as Operation
                                val secondValue = ex.pollFirst() as Number
                                val result = numbersStack.poll().value * (-1) * secondValue.value
                                numbersStack.push(Number(result))
                            }
                        }
                        else -> operatorsStack.push(item)
                    }
                } else {
                    item as Number
                    numbersStack.push(item)
                }
            } else {
                if (numbersStack.size == 1) {
                    return numbersStack.first.value
                }
                val second = numbersStack.poll()
                val first = numbersStack.poll()
                val operator = operatorsStack.poll()
                val result = operator.calculate(first.value, second.value)
                numbersStack.push(Number(result))
            }
        }
    }
}