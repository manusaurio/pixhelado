package dev.manusaurio.app

import dev.manusaurio.raycast.Wall

data class CharWall(override val collides: Boolean, val x: Int, val y: Int, val texture: Char) : Wall {
    override fun toString() = "<($x, $y), collides: $collides>"
}