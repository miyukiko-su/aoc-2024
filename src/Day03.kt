fun main() {
    runPart("Day03", "part1", ::addUpAllMultiplicationSumResults)
//    runPart("Day03", "part2", ::countAlmostSafeReports)
}

fun addUpAllMultiplicationSumResults(inputLines: List<String>): Int {
    var totalSum = 0

    inputLines.forEach { line ->
        val sum = sumUpMultiplicationsForLine(line)
        totalSum += sum
    }

    return totalSum
}

fun sumUpMultiplicationsForLine(inputLine: String): Int {
    var totalResult = 0

    for (i in 0..(inputLine.length - 8)) {
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
        totalResult += n1 * n2
    }

    return totalResult
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
    numbersPair.println()

    return numbersPair
}