package dev.manusaurio.app

/**
 * Returns the element at ([x], [y]) or `null` if the coordinates are out of bounds.
 */
fun <T> List<List<T>>.getOrNull(x: Int, y: Int): T? {
    return if (
        x in this.indices &&
        y in this[x].indices
    ) {
        this[x][y]
    }
    else null
}