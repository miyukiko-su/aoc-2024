import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readText

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun runPart(dayId: String, partId: String, executor: (List<String>) -> Int) {
    execute(
        executor = executor,
        testDataPath = "${dayId}_test-data.txt",
        testResultPath = "${dayId}_test-result_${partId}.txt",
        realDataPath = "${dayId}_real-data.txt"
    )
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

fun MutableList<Int>.insertWithAscendingOrder(newElement: Int) {
    if (this.size == 0) {
        this.add(newElement)
        return
    }

    for (i in 0..<this.size) {
        if (this[i] < newElement) {
            continue
        }

        this.add(i, newElement)
        return
    }

    this.add(newElement)
}
