fun main() {
    runPart("Day01", "part1", ::calculateDifferenceScore)
    runPart("Day01", "part2", ::calculateSimilarityScore)
}

fun calculateDifferenceScore(inputLines: List<String>): Int {
    val group1 = mutableListOf<Int>()
    val group2 = mutableListOf<Int>()

    inputLines.forEach { line ->
        val (number1, number2) = line.split("   ").map { number -> number.toInt() }
        group1.insertWithAscendingOrder(number1)
        group2.insertWithAscendingOrder(number2)
    }

    if (group1.size != group2.size) {
        return 0
    }

    var sum = 0
    for (i in 0..<group1.size) {
        val diff = group1[i] - group2[i]
        sum = when (diff < 0) {
            true -> sum - diff
            else -> sum + diff
        }
    }

    return sum
}

fun calculateSimilarityScore(inputLines: List<String>): Int {
    val group1 = mutableListOf<Int>()
    val group2ElementsOccurrence = mutableMapOf<Int, Int>()

    inputLines.forEach { line ->
        val (number1, number2) = line.split("   ").map { number -> number.toInt() }
        group1.insertWithAscendingOrder(number1)
        group2ElementsOccurrence.compute(number2) { _, v -> if (v == null) 1 else v + 1 }
    }

    var sum = 0
    for (number in group1) {
        sum += number * group2ElementsOccurrence.getOrDefault(number, 0)
    }

    return sum
}
