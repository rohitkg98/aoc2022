data class Item(val char: Char) {
    val priority = char.code - (if (char >= 'a') 'a'.code - 1 else ('A'.code - 27))
}

data class Compartment(val items: List<Item>)

class Rucksack(val left: Compartment, val right: Compartment) {
    companion object {
        fun fromString(items: String) : Rucksack {
            val items = items.map { Item(it) }

            val n = items.count()

            val left = Compartment(items.take(n/2))
            val right = Compartment(items.drop(n/2))

            return Rucksack(left, right)
        }
    }

    fun getCommon() = left.items.intersect(right.items.toSet()).first()

    fun allItems() = left.items.union(right.items)
}

class Group(val first: Rucksack, val second: Rucksack, val third: Rucksack) {
    fun getBadge() = first.allItems().intersect(second.allItems()).intersect(third.allItems()).first()
}

object Day3: Day() {
    override fun main() {
        val rucksacks = readInput("day3.txt").split("\n")
            .map { Rucksack.fromString(it) }

        val commonSum = rucksacks.sumOf { it.getCommon().priority }

        println("The sum of priorities of common items: $commonSum")

        val groups = rucksacks.chunked(3).map { Group(it[0], it[1], it[2]) }

        val badgeSum = groups.sumOf { it.getBadge().priority }

        println("The sum of priorities of group badges: $badgeSum")
    }
}