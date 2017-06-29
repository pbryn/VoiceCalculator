@file:Suppress("IllegalIdentifier")

package com.pawelbryniarski.voicecalculator.functional

import com.pawelbryniarski.voicecalculator.calculation.SimpleCalculator
import com.pawelbryniarski.voicecalculator.speechparser.polish.PolishSpeechParser
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Created by pawelbryniarski on 29.06.2017.
 */

@RunWith(Parameterized::class)
class ParserAndCalculatorTest(val input: String, val output: Int) {

    val parser = PolishSpeechParser()
    val calculator = SimpleCalculator()

    companion object {
        @JvmStatic
        @Parameterized.Parameters(
                name = """input = {0} |
                          output = {1}""")
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf("jeden plus dwa", 3),
                    arrayOf("jeden plus dwa odjąć trzy razy pięćdziesiąt dziewięć odjąć dwa razy trzy dodać pięć", -175))
        }
    }

    @Test
    fun `parses and calculates correctly`() {
        val actualOutput = calculator.calculate(parser.parse(input))
        assertEquals(output, actualOutput)
    }
}