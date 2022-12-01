object Day1 : Day() {
    override fun main() {
        val antCals = readInput("day1.txt").split("\n\n")
            .map { it.split("\n").sumOf { cal -> cal.toInt() } }

        val max = antCals.maxOf { it }

        println("Maximum calories carried by an Ant: $max")

        val top3Sum = antCals.sorted().takeLast(3).sum()

        println("Calories carried by top 3 Ants: $top3Sum")
    }
}