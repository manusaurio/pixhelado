package dev.manusaurio.raycast

import kotlin.math.abs

typealias World<T> = Array<out Array<out T>>

class BasicCamera <T : Wall> private constructor(
    private var position: Point,
    direction: Point,
    plane: Point,
    private val worldMap: World<T>,
    val columns: Int
)
    : Camera<T>
{
    val x: Double
        get() = position.x

    val y: Double
        get() = position.y

    var direction = direction
    private set

    var plane = plane
    private set

    companion object {
        operator fun <T : Wall>invoke(
            posX: Double,
            posY: Double,
            fov: Double, worldMap:
            World<T>, columns: Int
        ): BasicCamera<T> {
            /*
            Starting as:
                       |
                       |
                       |
                       |
            -----------+--------+--
                       |        Direction
                       |
                       + FOV y-value
                       |
             */
            return BasicCamera(
                position = Point(posX, posY),
                direction = Point(1.0, 0.0),
                plane = Point(0.0, fov),
                worldMap,
                columns
            )
        }
    }

    override fun rotate(angle: Double) {
        direction = direction.rotate(-angle)
        plane = plane.rotate(-angle)
    }

    override fun walk(units: Double) {
        position += direction * units
    }

    override fun cast(column: Int): Triple<T, Double, Boolean>? {
        val cameraX = 2.0 * column / columns - 1

        val rayDirX = direction.x + plane.x * cameraX
        val rayDirY = direction.y + plane.y * cameraX

        val deltaDistX = abs(1 / rayDirX)
        val deltaDistY = abs(1 / rayDirY)

        // current box
        var rayX = position.x.toInt()
        var rayY = position.y.toInt()


        var side = false

        var (stepX, sideDistX) = if (rayDirX < 0)
            -1 to (position.x - rayX) * deltaDistX
        else
            1 to (rayX + 1.0 - position.x) * deltaDistX

        var (stepY, sideDistY) = if (rayDirY < 0)
            -1 to (position.y - rayY) * deltaDistY
        else
            1 to (rayY + 1.0 - position.y) * deltaDistY

        do {
            if (sideDistX < sideDistY) {
                sideDistX += deltaDistX
                rayX += stepX
                side = false
            } else {
                sideDistY += deltaDistY
                rayY += stepY
                side = true
            }
        } while (!worldMap[rayX][rayY].collides)


        val perpWallDist = if (!side)
            (rayX - position.x + (1 - stepX) / 2) / rayDirX
        else
            (rayY - position.y + (1 - stepY) / 2) / rayDirY


        return Triple(worldMap[rayX][rayY], perpWallDist, side)
    }
}

