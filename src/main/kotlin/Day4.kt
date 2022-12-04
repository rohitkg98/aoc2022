data class Range(val start: Int, val end: Int) {
    companion object {
        fun fromString(str: String): Range {
            val (start, end) = str.split("-")

            return Range(start.toInt(), end.toInt())
        }
    }

    fun all() = start..end

    fun contains(range: Range): Boolean {
        return when {
            start <= range.start && end >= range.end -> true
            else -> false
        }
    }
}

object Day4 : Day() {
    override fun main() {
        val elfRanges = readInput("day4.txt").split("\n")
            .map { it.split(",").map { r -> Range.fromString(r) }.toPair() }

        val overlaps = elfRanges.map { it.first.contains(it.second) or it.second.contains(it.first) }.count { it }

        println("Total overlapping ranges: $overlaps")
    }
}
