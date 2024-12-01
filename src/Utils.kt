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
