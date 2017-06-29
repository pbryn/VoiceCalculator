package com.pawelbryniarski.voicecalculator.calculation

import com.pawelbryniarski.voicecalculator.speechparser.MathItem
import com.pawelbryniarski.voicecalculator.speechparser.MathItem.Number
import com.pawelbryniarski.voicecalculator.speechparser.MathItem.Operation
import java.util.*

class SimpleCalculator : Calculator {

    override fun calculate(expression: List<MathItem>): Int {
        val ex = LinkedList(expression)
        val numbersStack = LinkedList<Number>()
        val operatorsStack = LinkedList<Operation>()
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
                                // operation must be followed by "-"
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
                val first = numbersStack.pollLast()
                val second = numbersStack.pollLast()
                val operator = operatorsStack.pollLast()
                val result = operator.calculate(first.value, second.value)
                numbersStack.add(Number(result))
            }
        }
    }

    fun Operation.calculate(first: Int, second: Int): Int =
            when (value) {
                "+" -> first + second
                "-" -> first - second
                else -> throw  IllegalArgumentException()
            }
}

