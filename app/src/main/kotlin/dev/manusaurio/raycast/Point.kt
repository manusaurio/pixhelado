package dev.manusaurio.app

import kotlin.math.sqrt

class Point(val x: Double, val y: Double) {
    override fun toString() = "($x, $y)"

    operator fun plus(other: Point) =
        Point(x + other.x, y + other.y)

    operator fun minus(other: Point) =
        Point(x - other.x, y - other.y)

    operator fun times(n: Double) =
        Point(x * n, y * n)

    operator fun div(n: Double) =
        Point(x / n, y / n)

    fun magnitude() = sqrt(x*x + y*y)

    fun normalized() = when (val m = magnitude()) {
        0.0 -> Point(x, y)
        else -> this / m
    }

    infix fun distanceFrom(other: Point): Double {
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }
}