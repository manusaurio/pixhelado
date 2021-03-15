package dev.manusaurio.raycast

class Ray private constructor(
    val position: Point,
    val target: Point,
) {
    val direction: Point = Point(target.x - position.x, target.y - position.y)

    companion object {
        operator fun invoke(position: Point, target: Point): Ray {
            return Ray(
                position,
                target,
            )
        }

        operator fun invoke(positionX: Double, positionY: Double, targetX: Double, targetY: Double): Ray {
            return Ray(
                Point(positionX, positionY),
                Point(targetX, targetY),
            )
        }
    }

    // TODO: raycasting functions

    fun magnitude() = position.magnitude()

    operator fun component1() = position.x

    operator fun component2() = position.y

    operator fun component3() = target.x

    operator fun component4() = target.y

    fun findIntersection(line: Line): Point? {
        // based on Daniel Shiffman's line-line intersection finding implementation
        val x0 = line.a.x
        val y0 = line.a.y
        val x1 = line.b.x
        val y1 = line.b.y

        val x2 = position.x
        val y2 = position.y
        val x3 = position.x + direction.x
        val y3 = position.y + direction.y

        return when (val den = (x0 - x1) * (y2  - y3) - (y0 - y1) * (x2 - x3)) {
            0.0 -> null
            else -> {
                val t = ((x0 - x2) * (y2 - y3) - (y0 - y2) * (x2 - x3)) / den
                val u = -((x0 - x1) * (y0 - y2) - (y0 - y1) * (x0 - x2)) / den

                if (t > 0.0 && t < 1.0 && u > 0.0) {
                    Point(
                        x0 + t * (x1 - x0),
                        y0 + t * (y1 - y0)
                    )
                }
                else null
            }
        }
    }
}
