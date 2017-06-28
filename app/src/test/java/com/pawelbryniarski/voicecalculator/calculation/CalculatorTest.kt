package com.pawelbryniarski.voicecalculator.calculation

import com.pawelbryniarski.voicecalculator.texttransformation.MathItem
import com.pawelbryniarski.voicecalculator.texttransformation.MathItem.Number
import com.pawelbryniarski.voicecalculator.texttransformation.MathItem.Operation
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CalculatorTest(val expression: List<MathItem>, val expectedOutput: Int) {


    companion object {
        @JvmStatic
        @Parameterized.Parameters(
                name = """expression = {0} |
                          expectedOutput = {1}""")
        fun data(): Collection<Array<Any>> {
            return listOf(
                    arrayOf(listOf(Number(1)), 1),
                    arrayOf(listOf(Number(1), Operation("*"), Number(1)), 1),
                    arrayOf(listOf(Number(1), Operation("+"), Number(1)), 2),
                    arrayOf(listOf(Number(3), Operation("-"), Number(5)), -2),
                    arrayOf(listOf(Number(2), Operation("-"), Number(10)), -8),
                    arrayOf(listOf(Number(2), Operation("*"), Operation("-"), Number(10)), -20),
                    arrayOf(listOf(Number(2), Operation("+"), Number(3), Operation("*"), Number(2)),
                            8),
                    arrayOf(listOf(Number(2),
                            Operation("+"),
                            Number(10),
                            Operation("*"),
                            Operation("-"),
                            Number(2)), -18),
                    arrayOf(listOf(Number(2),
                            Operation("+"),
                            Number(5),
                            Operation("*"),
                            Number(8),
                            Operation("-"),
                            Number(10),
                            Operation("*"),
                            Number(11)), -68),
                    arrayOf(listOf(Number(2),
                            Operation("-"),
                            Number(3),
                            Operation("*"),
                            Number(5),
                            Operation("+"),
                            Number(10),
                            Operation("*"),
                            Number(12)), 107),

                    arrayOf(listOf(Number(9),
                            Operation("-"),
                            Number(2),
                            Operation("*"),
                            Number(1),
                            Operation("*"),
                            Number(5)), -1)
            )
        }
    }

    val tested = Calculator()

    @Test
    fun calculate() {
        assertEquals(expectedOutput, tested.calculate(expression))
    }
}

