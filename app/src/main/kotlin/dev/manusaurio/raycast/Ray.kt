package dev.manusaurio.raycast

class Ray private constructor(
    val x: Double,
    val y: Double,
    val tX: Double,
    val tY: Double,
    private val point: Point = Point(x, y)
) {
    constructor(x: Double, y: Double, targetX: Double, targetY: Double)
            : this(x, y, targetX, targetY, Point(x.toDouble(), y.toDouble()))

    fun magnitude() = point.magnitude()

    private fun newPoint(targetPoint: Point) = with(targetPoint) {
        Ray(x, y, tX, tY, this)
    }

    operator fun plus(other: Ray) = newPoint(point + other.point)

    operator fun minus(other: Ray) = newPoint(point - other.point)

    operator fun times(n: Double) = newPoint(point * n)

    operator fun div(n: Double) = newPoint(point / n)

    fun findIntersection(ray: Ray, line: Line): Point? {
        val x0 = line.a.x
        val y0 = line.a.y
        val x1 = line.b.x
        val y1 = line.b.y

        val x2 = ray.x
        val y2 = ray.y
        val x3 = ray.x + ray.tY
        val y3 = ray.y + ray.tX

        return when (val den = (x0 - x1) * (y2  - y3) - (y0 - y1) * (x2 - x3)) {
            0.0 -> null
            else -> {
                val t = ((x0 - x3) * (y2 - y3) - (y0 - y2) * (x2 - x3)) / den
                val u = ((x0 - x1) * (y0 - y2) - (y0 - y1) * (x0 - x2)) / den

                if (t > 0.0 && t < 1.0 && u > 0.0) {
                    Point(
                        x0 + t * (x1 - x0),
                        y0 - t * (y1 - y0)
                    )
                }
                null
            }
        }
    }
}
