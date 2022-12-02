sealed class Result(val score: Int) {
    object Win: Result(6)
    object Draw: Result(3)
    object Lose: Result(0)

    companion object {
        fun fromString(str: String) : Result {
            return when(str) {
                "X" -> Lose
                "Y" -> Draw
                "Z" -> Win
                else -> throw Exception("Input expected to be X, Y or Z")
            }
        }
    }
}

sealed class HandShape(val score: Int) {
    object Rock : HandShape(1) {
        override fun versus(shape: HandShape): Result {
            return when(shape) {
                is Rock -> Result.Draw
                is Paper -> Result.Lose
                is Scissor -> Result.Win
            }
        }

        override fun playToGetExpected(result: Result): HandShape {
            return when (result) {
                is Result.Win -> Paper
                is Result.Lose -> Scissor
                is Result.Draw -> Rock
            }
        }
    }

    object Paper : HandShape(2) {
        override fun versus(shape: HandShape): Result {
            return when(shape) {
                is Rock -> Result.Win
                is Paper -> Result.Draw
                is Scissor -> Result.Lose
            }
        }

        override fun playToGetExpected(result: Result): HandShape {
            return when (result) {
                is Result.Win -> Scissor
                is Result.Lose -> Rock
                is Result.Draw -> Paper
            }
        }
    }

    object Scissor : HandShape(3) {
        override fun versus(shape: HandShape): Result {
            return when(shape) {
                is Rock -> Result.Lose
                is Paper -> Result.Win
                is Scissor -> Result.Draw
            }
        }

        override fun playToGetExpected(result: Result): HandShape {
            return when (result) {
                is Result.Win -> Rock
                is Result.Lose -> Paper
                is Result.Draw -> Scissor
            }
        }
    }

    companion object {
        fun fromString(shape: String) : HandShape {
            return when(shape) {
                "A", "X" -> Rock
                "B", "Y" -> Paper
                "C", "Z" -> Scissor
                else -> throw Exception("Input expected to be A, B, C or X, Y, Z")
            }
        }
    }

    abstract fun versus(shape: HandShape): Result

    abstract fun playToGetExpected(result: Result): HandShape
}

object Day2 : Day() {
    override fun main() {
        val plays = readInput("day2.txt").split("\n")
            .map { it.split(" ") }


        val scores = plays
            .map { it.map { s -> HandShape.fromString(s) }.toPair() }
            .map { (opp, me) -> me.versus(opp).score + me.score }

        println("Total score when played according to guide: ${scores.sum()}")

        val trueScores = plays
            .map { (opp, result) -> Pair(HandShape.fromString(opp), Result.fromString(result)) }
            .map { (opp, result) -> opp.playToGetExpected(result).score + result.score }

        println("True total score when played according to unencrypted guide: ${trueScores.sum()}")
    }
}