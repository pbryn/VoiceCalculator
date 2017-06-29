package com.pawelbryniarski.voicecalculator.speechparser.polish

import com.pawelbryniarski.voicecalculator.speechparser.MathItem
import com.pawelbryniarski.voicecalculator.speechparser.SpeechParser

class PolishSpeechParser : SpeechParser {

    private val singlePartNumbers = mapOf(
            "zero" to 0,
            "jeden" to 1,
            "dwa" to 2,
            "trzy" to 3,
            "czteru" to 4,
            "pięć" to 5,
            "sześć" to 6,
            "siedem" to 7,
            "osiem" to 8,
            "dziewięć" to 9,
            "dziesięć" to 10,
            "jedynaście" to 11,
            "dwanaście" to 12,
            "trzynaście" to 13,
            "czternaście" to 14,
            "piętnaście" to 15,
            "szesnaście" to 16,
            "siedemnaście" to 17,
            "osiemnaście" to 18,
            "dziewiętnaście" to 19,
            "dwadzieścia" to 20)

    private val singleDigitNumbers = mapOf(
            "zero" to 0,
            "jeden" to 1,
            "dwa" to 2,
            "trzy" to 3,
            "czteru" to 4,
            "pięć" to 5,
            "sześć" to 6,
            "siedem" to 7,
            "osiem" to 8,
            "dziewięć" to 9)

    private val compoundNumbers = mapOf(
            "dwadzieścia" to 20,
            "trzydzieści" to 30,
            "czterdzieści" to 40,
            "pięćdziesiąt" to 50,
            "sześćdziesiąt" to 60,
            "siedemdziesiąt" to 70,
            "osiemdziesiąt" to 80,
            "dziewięćdziesiąt" to 90)

    private val compoundNumbersSet: Set<Int> = setOf(20,
            30,
            40,
            50,
            60,
            70,
            80,
            90)

    override fun parse(text: String): List<MathItem> {
        return text.split(" ").map { it.toLowerCase() }.fold(mutableListOf<MathItem>()) {
            mathItems: MutableList<MathItem>, currentWord ->
            if (isOperation(currentWord)) {
                mathItems.add(transformOperations(currentWord))
            } else {
                transformNumber(mathItems, currentWord)
            }
            mathItems
        }
    }

    private fun transformNumber(mathItems: MutableList<MathItem>, currentWord: String) {
        if (currentWord.toIntOrNull() != null) {
            mathItems.add(MathItem.Number(currentWord.toInt()))
        } else if (mathItems.isEmpty()) {
            addNumber(currentWord, mathItems)
        } else if (mathItems.last() is MathItem.Number) {
            val last: Int = (mathItems.last() as MathItem.Number).value
            if (compoundNumbersSet.contains(last) && singleDigitNumbers.containsKey(currentWord)) {
                val currentNumber: Int = singlePartNumbers[currentWord]!!
                mathItems.removeAt(mathItems.lastIndex)
                mathItems.add(MathItem.Number(last + currentNumber))
            } else {
                addNumber(currentWord, mathItems)
            }
        } else {
            addNumber(currentWord, mathItems)
        }
    }

    private fun addNumber(currentWord: String,
                          mathItems: MutableList<MathItem>) {
        if (singlePartNumbers.containsKey(currentWord)) {
            mathItems.add(MathItem.Number(singlePartNumbers[currentWord]!!))
        } else if (compoundNumbers.containsKey(currentWord)) {
            mathItems.add(MathItem.Number(compoundNumbers[currentWord]!!))
        }
    }

    private fun transformOperations(operation: String) =
            if (operation == "plus" || operation == "dodać" || operation == "+") {
                MathItem.Operation("+")
            } else if (operation == "minus" || operation == "odjąć" || operation == "-") {
                MathItem.Operation("-")
            } else {
                MathItem.Operation("*")
            }

    private fun isOperation(word: String): Boolean =
                    word == "plus" || word == "minus" || word == "razy" ||
                    word == "odjąć" || word == "dodać" || word == "+" || word == "-" || word == "x"
}