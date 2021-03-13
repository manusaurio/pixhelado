package dev.manusaurio.raycast

class Ray private constructor(
    val x: Int,
    val y: Int,
    val tX: Int,
    val tY: Int,
    private val point: Point = Point(x.toDouble(), y.toDouble())
) {
    constructor(x: Int, y: Int, targetX: Int, targetY: Int)
            : this(x, y, targetX, targetY, Point(x.toDouble(), y.toDouble()))

    fun magnitude() = point.magnitude()

    private fun newPoint(targetPoint: Point) = with (targetPoint) {
        Ray(x.toInt(), y.toInt(), tX, tY, this)
    }

    operator fun plus(other: Ray) = newPoint(point + other.point)

    operator fun minus(other: Ray) = newPoint(point - other.point)

    operator fun times(n: Double) = newPoint(point * n)

    operator fun div(n: Double) = newPoint(point / n)
}
