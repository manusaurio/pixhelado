package dev.manusaurio.app

import kotlin.math.sqrt

class Vector(val x: Double, val y: Double, val z: Double = 0.0) {
    override fun toString() = "($x, $y, $z)"

    operator fun plus(other: Vector) =
        Vector(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vector) =
        Vector(x - other.x, y - other.y, z - other.z)

    operator fun times(n: Double) =
        Vector(x * n, y * n, z * n)

    operator fun div(n: Double) =
        Vector(x / n, y / n, z / n)

    fun magnitude() = sqrt(x*x + y*y + z*z)

    fun normalized() = when (val m = magnitude()) {
        0.0 -> Vector(x, y, z)
        else -> this / m
    }

    infix fun distanceFrom(other: Vector): Double {
        val dx = x - other.x
        val dy = y - other.y
        val dz = z - other.z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }
}