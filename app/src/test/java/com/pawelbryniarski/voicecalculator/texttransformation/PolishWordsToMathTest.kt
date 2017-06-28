@file:Suppress("IllegalIdentifier")

package com.pawelbryniarski.voicecalculator.texttransformation

import com.pawelbryniarski.voicecalculator.texttransformation.MathItem.Number
import com.pawelbryniarski.voicecalculator.texttransformation.MathItem.Operation
import com.pawelbryniarski.voicecalculator.texttransformation.polish.PolishSpeechToMath
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class PolishWordsToMathTest(val inputText: String, val outputExpressions: List<MathItem>) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(
                name = """input = {0} |
                          expectedOutput = {1}""")
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf("jeden", listOf(Number(1))),
                    arrayOf("jedynaście", listOf(Number(11))),
                    arrayOf("dwadzieścia jeden", listOf(Number(21))),
                    arrayOf("sześćdziesiąt dziewięć", listOf(Number(69))),
                    arrayOf("69 - 4", listOf(Number(69),Operation("-"), Number(4))),
                    arrayOf("jeden plus dwa", listOf(Number(1), Operation("+"), Number(2))),
                    arrayOf("Jeden pluS Dwa", listOf(Number(1), Operation("+"), Number(2))),
                    arrayOf("sześć razy osiem", listOf(Number(6), Operation("*"), Number(8))),
                    arrayOf("trzydzieści dwa razy osiem minus pięć plus dwa razy trzydzieści",
                            listOf(Number(32),
                                   Operation("*"),
                                   Number(8),
                                   Operation("-"),
                                   Number(5),
                                   Operation("+"),
                                   Number(2),
                                   Operation("*"),
                                   Number(30))))
        }
    }

    val tested: SpeechToMathExpression = PolishSpeechToMath()

    @Test
    fun `transforms text to list of math expressions`() {
        assertEquals(outputExpressions, tested.transform(inputText))
    }
}