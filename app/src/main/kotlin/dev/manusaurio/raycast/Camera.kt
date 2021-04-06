package dev.manusaurio.raycast

interface Camera<T> {
    fun cast(column: Int): Triple<T, Double, Boolean>?
    fun rotate(angle: Double)
    fun walk(units: Double)
}