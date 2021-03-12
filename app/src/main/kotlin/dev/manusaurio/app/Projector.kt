package dev.manusaurio.app

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.grid.TileGrid
import kotlin.math.abs

// Bresenham's line algorithm
// based on https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
class Projector private constructor(
    private val tileGrid: TileGrid,
    private val tile: Tile,
) {
    private val positions: List<List<Position>> = tileGrid.run {
        (0..width).map { x ->
            (0..height).map { y ->
                Position.create(x, y)
            }
        }
    }

    companion object {
        private const val defaultChar = '#'

        operator fun invoke(tileGrid: TileGrid, styleSet: StyleSet = StyleSet.defaultStyle(), char: Char = defaultChar) : Projector {
            return Projector(tileGrid, Tile.createCharacterTile(char, styleSet))
        }

        fun TileGrid.createProjector(styleSet: StyleSet = StyleSet.defaultStyle(), char: Char = defaultChar): Projector {
            return Projector(this, Tile.createCharacterTile(char, styleSet))
        }
    }

    fun plotLine(
        x0: Int,
        y0: Int,
        x1: Int,
        y1: Int,
    ) {
        fun plotLineLow(x0: Int, y0: Int, x1: Int, y1: Int) {
            var dx = x1 - x0
            var dy = y1 - y0
            var yi = 1
            if (dy < 0) {
                yi = -1
                dy = -dy
            }
            var d = (2 * dy) - dx
            var y = y0

            for (x in x0..x1) {
                positions.getOrNull(x)?.getOrNull(y)?.let {
                    tileGrid.draw(tile, it)
                }

                if (d > 0) {
                    y += yi
                    d += 2 * (dy - dx)
                } else {
                    d += 2 * dy
                }
            }
        }

        fun plotLineHigh(x0: Int, y0: Int, x1: Int, y1: Int) {
            var dx = x1 - x0
            var dy = y1 - y0
            var xi = 1
            if (dx < 0) {
                xi = -1
                dx = -dx
            }
            var d = (2 * dx) - dy
            var x = x0

            for (y in y0..y1) {
                positions.getOrNull(x)?.getOrNull(y)?.let {
                    tileGrid.draw(tile, it)
                }
                
                if (d > 0) {
                    x += xi
                    d += 2 * (dx - dy)
                } else {
                    d += 2 * dx
                }
            }
        }

        if (abs(y1 - y0) < abs(x1 - x0)) {
            if (x0 > x1) plotLineLow(x1, y1, x0, y0)
            else plotLineLow(x0, y0, x1, y1)
        } else {
            if (y0 > y1) plotLineHigh(x1, y1, x0, y0)
            else plotLineHigh(x0, y0, x1, y1)
        }
    }
}