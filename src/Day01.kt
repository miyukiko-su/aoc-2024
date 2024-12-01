fun main() {
    fun runPart(partId: String, executor: (List<String>) -> Int) {
        execute(
            executor = executor,
            testDataPath = "Day01_test-data.txt",
            testResultPath = "Day01_test-result_${partId}.txt",
            realDataPath = "Day01_real-data.txt"
        )
    }

    runPart("part1", ::calculateDifferenceScore)
    runPart("part2", ::calculateSimilarityScore)
}

fun execute(executor: (List<String>) -> Int, testDataPath: String, testResultPath: String, realDataPath: String) {
    val testInput = readInput(testDataPath)
    val testResult = executor(testInput)

    val expectedResult = readInput(testResultPath).first().toInt()
    "Expected result for the test data: $expectedResult".println()
    "Received result for the test data: $testResult".println()
    check(testResult == expectedResult)

    val realInput = readInput(realDataPath)
    val result = executor(realInput)
    "Result for the actual data: $result".println()
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
