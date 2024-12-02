fun main() {
    runPart("Day02", "part1", ::countSafeReports)
    runPart("Day02", "part2", ::countAlmostSafeReports)
}

fun countSafeReports(inputLines: List<String>): Int {
    var safeReportsCount = 0

    inputLines.forEach { line ->
        val numbers = line.split(" ").map { number -> number.toInt() }
        val isSafe = checkIsReportSafeForGivenNumbers(numbers)
        if (isSafe) {
            safeReportsCount++
        }
    }

    return safeReportsCount
}

fun countAlmostSafeReports(inputLines: List<String>): Int {
    var safeReportsCount = 0

    inputLines.forEach { line ->
        val numbers = line.split(" ").map { number -> number.toInt() }
        for (i in 0..<numbers.count()) {
            val numbersPartition = numbers.filterIndexed { index, _ -> index != i }
            val isSafe = checkIsReportSafeForGivenNumbers(numbersPartition)
            if (isSafe) {
                safeReportsCount++
                break
            }
        }
    }

    return safeReportsCount
}

fun checkIsReportSafeForGivenNumbers(numbers: List<Int>): Boolean {
    var isPositiveDirection: Boolean? = null
    for (i in 1..<numbers.count()) {
        if (numbers[i] == numbers[i - 1]) {
            return false
        }
        val isIterationDirectionPositive = numbers[i] > numbers[i - 1]
        if (isPositiveDirection == null) {
            isPositiveDirection = isIterationDirectionPositive
        }
        else if (isPositiveDirection != isIterationDirectionPositive) {
            return false
        }
        val diff = if (isIterationDirectionPositive) numbers[i] - numbers[i - 1] else numbers[i - 1] - numbers[i]
        if (diff < 1  || diff > 3) {
            return false
        }
    }

    return true
}