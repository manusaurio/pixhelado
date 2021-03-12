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

class Vector(val x: Double, val y: Double, val z: Double = .0) {
    override fun toString() = "($x, $y, $z)"

    operator fun div(n: Double) =
        Vector(x / n, y / n, z / n)

    fun magnitude() = sqrt(x*x + y*y + z*z)

    fun normalized() = when (val m = magnitude()) {
        0.0 -> Vector(x, y, z)
        else -> this / m
    }
    // more stuff
}


/*
class TargetingPoint private constructor(
    val x: Int,
    val y: Int,
    val targetX: Int,
    val targetY: Int,
    private val vector: Vector,
) {
    constructor(x: Int, y: Int, tX: Int, tY: Int)
            : this(x, y, tX, tY, Vector(x.toDouble(), y.toDouble()))

    fun magnitude() = vector.magnitude()

    operator fun div(n: Double) = with (vector / n) {
        TargetingPoint(x.toInt(), y.toInt(), targetX, targetY, this)
    }
}*/