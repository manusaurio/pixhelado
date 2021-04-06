package dev.manusaurio.app

import org.hexworks.zircon.api.*
import dev.manusaurio.raycast.BasicCamera
import dev.manusaurio.raycast.Wall
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.*

class Canvas<T : Wall>(
    private val surface: DrawSurface,
    posX: Double, posY: Double,
    private val world: Array<out Array<out T>>,
    val camera: BasicCamera<T> = BasicCamera(posX, posY, 0.7, world, surface.width)
) {
    private val tileBuffer = DrawSurfaces.tileGraphicsBuilder().withSize(surface.size).build()
    private val rows = surface.height

    private val positions: List<List<Position>> = surface.run {
        (0..width).map { x ->
            (0..height).map { y ->
                Position.create(x, y)
            }
        }
    }

    fun draw(f: Triple<T, Double, Boolean>.() -> Tile?) {
        tileBuffer.clear()

        for (column in 0..camera.columns) {
            val cast = camera.cast(column)?:return

            val lineHeight =  (rows / cast.second).toInt()

            val drawStart = (-lineHeight / 2.0 + rows / 2.0).toInt().coerceIn(0..rows)
            val drawEnd = (lineHeight / 2.0 + rows / 2.0).toInt().coerceIn(0..rows)

            for (row in drawStart..drawEnd) {
                val side = cast.third

                tileBuffer.draw(
                    f(cast)?:Tile.defaultTile(), positions[column][row]
                )
            }
        }
        surface.draw(tileBuffer)
    }
}