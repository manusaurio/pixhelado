package dev.manusaurio.app

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.grid.TileGrid
import kotlin.math.abs

// Bresenham's line algorithm
// based on https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm

// TODO: remove hardcoded values & refactor if necessary

fun TileGrid.plotLine(x0: Int, y0: Int, x1: Int, y1: Int) {
    if (abs(y1 - y0) < abs(x1 - x0)) {
        if (x0 > x1) plotLineLow(x1, y1, x0, y0)
        else plotLineLow(x0, y0, x1, y1)
    } else {
        if (y0 > y1) plotLineHigh(x1, y1, x0, y0)
        else plotLineHigh(x0, y0, x1, y1)
    }
}

fun TileGrid.plotLineLow(x0: Int, y0: Int, x1: Int, y1: Int) {
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
        this.draw(Tile.createCharacterTile('X', StyleSet.defaultStyle()), Position.create(x, y))
        if (d > 0) {
            y += yi
            d += 2 * (dy - dx)
        } else {
            d += 2 * dy
        }
    }
}

fun TileGrid.plotLineHigh(x0: Int, y0: Int, x1: Int, y1: Int) {
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
        this.draw(Tile.createCharacterTile('X', StyleSet.defaultStyle()), Position.create(x, y))
        if (d > 0) {
            x += xi
            d += 2 * (dx - dy)
        } else {
            d += 2 * dx
        }
    }
}