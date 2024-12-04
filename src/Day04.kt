fun main() {
    execute(
        executor = ::countAllXmasEntriesInText,
        testDataPath = "Day04_test-data_part1.txt",
        testResultPath = "Day04_test-result_part1.txt",
        realDataPath = "Day04_real-data.txt"
    )
    execute(
        executor = ::countAllCrossMasEntriesInText,
        testDataPath = "Day04_test-data_part2.txt",
        testResultPath = "Day04_test-result_part2.txt",
        realDataPath = "Day04_real-data.txt"
    )
}

enum class Direction {
    E, NE, N, NW, W, SW, S, SE
}

val yxVectorByDirection = mutableMapOf(
    Direction.E to Pair(0, 1),
    Direction.NE to Pair(1, 1),
    Direction.N to Pair(1, 0),
    Direction.NW to Pair(1, -1),
    Direction.W to Pair(0, -1),
    Direction.SW to Pair(-1, -1),
    Direction.S to Pair(-1, 0),
    Direction.SE to Pair(-1, 1)
)

fun countAllXmasEntriesInText(textFileAsLines: List<String>): Int {
    val foundEntries = mutableMapOf<Pair<Int, Int>, MutableList<Direction>>()

    for (y in textFileAsLines.indices) {
        val maxY = textFileAsLines.size - 1
        val row = textFileAsLines[y]

        for (x in row.indices) {
            val maxX = row.length - 1
            val cell = row[x]

            if (cell != 'X') {
                continue
            }

            val alreadyFoundDirectionsForThisCell = foundEntries.getOrDefault(Pair(y, x), mutableListOf())
            for (direction in Direction.entries.filter { !alreadyFoundDirectionsForThisCell.contains(it) }) {
                val (shiftY, shiftX) = yxVectorByDirection[direction]!!
                val xmasWord = "XMAS"

                if (xmasWord.indices.any { i ->
                        val xAdaptive = x + i * shiftX
                        val yAdaptive = y + i * shiftY

                        return@any xAdaptive < 0 || xAdaptive > maxX || yAdaptive < 0 || yAdaptive > maxY
                                || textFileAsLines[yAdaptive][xAdaptive] != xmasWord[i]
                    }) {
                    continue
                }

                foundEntries.getOrPut(Pair(y, x)) { mutableListOf() }.add(direction)
            }
        }
    }

    return foundEntries.values.flatten().size
}

fun countAllCrossMasEntriesInText(textFileAsLines: List<String>): Int {
    var totalCount = 0

    for (y in 1..<(textFileAsLines.size-1)) {
        val row = textFileAsLines[y]

        for (x in 1..<(row.length-1)) {
            val cell = row[x]

            if (cell != 'A') {
                continue
            }

            fun getNeighbourCellByDirection(direction: Direction): Char {
                val (shiftX, shiftY) = yxVectorByDirection[direction]!!

                return textFileAsLines[y + shiftY][x + shiftX]
            }

            val cNW = getNeighbourCellByDirection(Direction.NW)
            val cSW = getNeighbourCellByDirection(Direction.SW)
            val cNE = getNeighbourCellByDirection(Direction.NE)
            val cSE = getNeighbourCellByDirection(Direction.SE)

            if (!(cNW == 'M' && cSE == 'S' || cNW == 'S' && cSE == 'M') ||
                !(cNE == 'M' && cSW == 'S' || cNE == 'S' && cSW == 'M')) {
                continue
            }

            totalCount++
        }
    }

    return totalCount
}