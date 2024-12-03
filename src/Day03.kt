fun main() {
    execute(
        executor = ::addUpAllMultiplicationSumResults,
        testDataPath = "Day03_test-data_part1.txt",
        testResultPath = "Day03_test-result_part1.txt",
        realDataPath = "Day03_real-data.txt"
    )
    execute(
        executor = ::addUpAllMultiplicationSumResultsWithConditions,
        testDataPath = "Day03_test-data_part2.txt",
        testResultPath = "Day03_test-result_part2.txt",
        realDataPath = "Day03_real-data.txt"
    )
}

fun addUpAllMultiplicationSumResults(inputLines: List<String>): Int =
    addUpAllMultiplicationSumResultsInternal(inputLines)

fun addUpAllMultiplicationSumResultsWithConditions(inputLines: List<String>): Int =
    addUpAllMultiplicationSumResultsInternal(inputLines, isConditionActive = true)

fun addUpAllMultiplicationSumResultsInternal(inputLines: List<String>, isConditionActive: Boolean = false): Int {
    var totalSumResult = 0

    var isMultiplicationEnabled = true

    inputLines.forEach { inputLine ->
        var totalSumForLine = 0

        for (i in 0..(inputLine.length - 8)) {
            if (isConditionActive) {
                if (isMultiplicationEnabled) {
                    if (inputLine[i] == 'd' &&
                        inputLine[i + 1] == 'o' &&
                        inputLine[i + 2] == 'n' &&
                        inputLine[i + 3] == '\'' &&
                        inputLine[i + 4] == 't' &&
                        inputLine[i + 5] == '(' &&
                        inputLine[i + 6] == ')') {
                        isMultiplicationEnabled = false
                        continue
                    }
                }
                else {
                    if (inputLine[i] != 'd' ||
                        inputLine[i + 1] != 'o' ||
                        inputLine[i + 2] != '(' ||
                        inputLine[i + 3] != ')') continue
                    isMultiplicationEnabled = true
                    continue
                }
            }

            if (inputLine[i] != 'm') continue

            var closingBracketIndex: Int? = null
            for (j in i + 7..(if (i+11 < inputLine.length) i + 11 else inputLine.length - 1)) {
                if (inputLine[j] != ')') continue

                closingBracketIndex = j
                break
            }

            if (closingBracketIndex == null) continue

            val substring = inputLine.substring(i, closingBracketIndex + 1)
            val pairSearchResult = tryGetMultiplicationPair(substring) ?: continue

            val (n1, n2) = pairSearchResult
            totalSumForLine += n1 * n2
        }

        totalSumResult += totalSumForLine
    }

    return totalSumResult
}

fun tryGetMultiplicationPair(stringFragment: String): Pair<Int, Int>? {
    if (stringFragment.length < 8 || stringFragment.length > 12) return null
    if (stringFragment[0] != 'm') return null
    if (stringFragment[1] != 'u') return null
    if (stringFragment[2] != 'l') return null
    if (stringFragment[3] != '(') return null
    if (stringFragment.last() != ')') return null

    val inBracketsFragment = stringFragment.substring(4, stringFragment.length - 1)
    val numbersRaw = inBracketsFragment.split(",")

    if (numbersRaw.size != 2) return null

    val (numberRaw1: String, numberRaw2: String) = numbersRaw

    if (numberRaw1.isEmpty() || numberRaw1.length > 3) return null
    if (numberRaw2.isEmpty() || numberRaw2.length > 3) return null
    if (numberRaw1.any { !it.isDigit() } || numberRaw2.any { !it.isDigit() }) return null

    val numbersPair = Pair(numberRaw1.toInt(), numberRaw2.toInt())

    return numbersPair
}