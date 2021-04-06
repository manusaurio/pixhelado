package dev.manusaurio.app

import dev.manusaurio.raycast.Camera
import dev.manusaurio.raycast.Wall
import kotlin.math.abs

class Player<T : Wall>(
    camera: Camera<T>,
    var radiansPerSecond: Double = 1.75,
    var blocksPerSecond: Double = 1.25
) : Camera<T> by camera {

    fun rotate(clockwise: Boolean) = rotate (
        (radiansPerSecond * deltaTimeSeconds).let {
            if (clockwise) -it else it
        }
    )


    fun walk(forward: Boolean) = walk(
        (blocksPerSecond * deltaTimeSeconds).let {
            if (forward) it else -it
        }
    )
}