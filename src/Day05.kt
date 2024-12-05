fun main() {
    execute(
        executor = ::sumUpMiddlesInCorrectUpdates,
        testDataPath = "Day05_test-data_part1.txt",
        testResultPath = "Day05_test-result_part1.txt",
        realDataPath = "Day05_real-data.txt"
    )
    execute(
        executor = ::sumUpMiddlesInIncorrectUpdatesAfterFix,
        testDataPath = "Day05_test-data_part2.txt",
        testResultPath = "Day05_test-result_part2.txt",
        realDataPath = "Day05_real-data.txt"
    )
}

fun separateInputs(textFileAsLines: List<String>): Pair<MutableMap<Int, MutableList<Int>>, MutableList<List<Int>>> {
    val followersByTargetNumber = mutableMapOf<Int, MutableList<Int>>()
    val updates = mutableListOf<List<Int>>()

    for (line in textFileAsLines) {
        if (line.trim().isEmpty()) continue
        when {
            line.contains('|') -> {
                val (targetNumber, follower) = line.trim().split('|').map { it.toInt() }
                followersByTargetNumber.getOrPut(targetNumber) { mutableListOf() }.add(follower)
            }
            line.contains(',') -> {
                val update = line.trim().split(',').map { it.toInt() }
                updates.add(update)
            }
        }
    }

    return Pair(followersByTargetNumber, updates)
}

fun checkIsUpdateValid(followersByTargetNumber: MutableMap<Int, MutableList<Int>>, update: List<Int>): Boolean {
    val processedNumbers = mutableListOf<Int>()
    for (number in update) {
        if (followersByTargetNumber.getOrElse(number) { mutableListOf() }
                .any { follower -> processedNumbers.contains(follower) }) {
            return false
        }

        processedNumbers.add(number)
    }

    return true
}

fun sumUpMiddlesInCorrectUpdates(textFileAsLines: List<String>): Int {
    var sum = 0
    val (followersByTargetNumber, updates) = separateInputs(textFileAsLines)
    for (update in updates) {
        val isUpdateValid = checkIsUpdateValid(followersByTargetNumber, update)
        if (!isUpdateValid) continue

        val middleElement = update[(update.size - 1) / 2]
        sum += middleElement
    }

    return sum
}

fun sumUpMiddlesInIncorrectUpdatesAfterFix(textFileAsLines: List<String>): Int {
    var sum = 0
    val (followersByTargetNumber, updates) = separateInputs(textFileAsLines)
    for (update in updates) {
        val isUpdateValid = checkIsUpdateValid(followersByTargetNumber, update)
        if (isUpdateValid) continue

        var updateFixed = update
        while (!checkIsUpdateValid(followersByTargetNumber, updateFixed)) {
            updateFixed = tryFixOrderInUpdate(followersByTargetNumber, updateFixed)
        }
        val middleElement = updateFixed[(updateFixed.size - 1) / 2]
        sum += middleElement
    }

    return sum
}

fun tryFixOrderInUpdate(followersByTargetNumber: MutableMap<Int, MutableList<Int>>, update: List<Int>): List<Int> {
    val newUpdate = mutableListOf<Int>()
    val processedNumbers = mutableMapOf<Int, MutableList<Int>>()
    for (i in update.indices) {
        val number = update[i]
        val problematicAncestors = followersByTargetNumber.getOrElse(number) { mutableListOf() }
            .filter { follower -> processedNumbers.containsKey(follower) }
        if (problematicAncestors.isNotEmpty()) {
            val earliestProblem = problematicAncestors.first()
            val indexOfProblem = processedNumbers[earliestProblem]!!.first()
            newUpdate.add(indexOfProblem, number)
            for (k in processedNumbers.keys) {
                for (j in processedNumbers[k]!!.indices) {
                    processedNumbers[k]!![j]++
                }
            }
        }
        else {
            newUpdate.add(number)
        }
        processedNumbers.getOrPut(number) { mutableListOf() }.add(i)
    }

    return newUpdate
}