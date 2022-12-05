data class Crate(val letter: Char) {
    override fun toString(): String {
        return "$letter"
    }
}

data class Move(val from: Int, val to: Int, val n: Int) {
    companion object {
        fun fromString(moveStr: String): Move {
            val moveRegex = Regex("move (\\d+) from (\\d) to (\\d)")

            return moveRegex.find(moveStr)
                ?.destructured
                ?.let { (n, from, to) -> Move(from.toInt(), to.toInt(), n.toInt()) }
                ?: throw IllegalArgumentException("Move is not properly structured '$moveStr'")
        }
    }
}

open class Ship(private val stacks: Array<ArrayDeque<Crate>>) {
    companion object {
        fun fromString(cratesStr: String): Ship {
            val (indicesStr, crates) = cratesStr.split("\n").reversed().headTail()

            val indices = indicesStr.trim().split("   ").map { it.toInt() }

            val stacks = Array(indices.size + 1) { ArrayDeque<Crate>() }

            crates.map { crateLine ->
                crateLine.chunked(4).map { it.trim() }
                    .zip(indices)
                    .filter { (s, _) -> s.isNotEmpty() }
                    .forEach {(s, i) -> stacks[i].add(Crate(s[1]))}
            }

            return Ship(stacks)
        }
    }

    fun apply(move: Move) {
        repeat(move.n) {
            val crate = stacks[move.from].removeLast()

            stacks[move.to].add(crate)
        }
    }

    fun apply9001(move: Move) {
        val crates = stacks[move.from].takeLast(move.n)

        repeat(move.n) {stacks[move.from].removeLast()}

        stacks[move.to].addAll(crates)
    }

    fun getTops() = stacks.filter { it.isNotEmpty() }.map { it.last() }
}

object Day5 : Day() {
    override fun main() {
        val (crates, movesStr) = readInput("day5.txt").split("\n\n")

        val ship = Ship.fromString(crates)
        val moves = movesStr.split("\n").map { Move.fromString(it) }

        moves.forEach { ship.apply(it) }

        println("The tops of each stack concatted: ${ship.getTops().joinToString("")}")

        val ship9001 = Ship.fromString(crates)

        moves.forEach { ship9001.apply9001(it) }

        println("The tops of each stack concatted for 9001 crane: ${ship9001.getTops().joinToString("")}")
    }
}
