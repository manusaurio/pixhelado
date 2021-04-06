package dev.manusaurio.app

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.uievent.KeyCode
import kotlin.system.exitProcess


// TODO: Move CoreKt to its own class (the `main` function along with the time variables)
var nanoTimeStamp = System.nanoTime()
    private set

var deltaTimeSeconds =  (System.nanoTime() - nanoTimeStamp) / 1e9
    private set

var frameRate: Double = 0.0
    private set


fun main() {
    val app = LibgdxApplications.startApplication(
        AppConfig.newBuilder().withSize(96, 64).build(),
        LwjglApplicationConfiguration().apply {
            backgroundFPS = 15
            forceExit = true
            // this is necessary in i3wm:
            resizable = false

        }
    )

    // XXX: Move the `object {}.javaClass.getResource` part to HelperFunctions
    // TODO: redesign & move to its own class
    val world = object {}.javaClass.getResource("DefaultMap.txt")
        .readText().lineSequence()
        .mapIndexed { x, string ->
            string.split(',').mapIndexed { y, wall ->
                CharWall(wall != "0", x, y,
                    when (wall) {
                        "1" -> '#'
                        "2" -> '@'
                        else -> '·'
                    }
                )
            }.toTypedArray()
        }
        .toList()
        .toTypedArray()

    val canvas = Canvas(app.tileGrid, 7.5, 2.5, world)
    val player = Player(canvas.camera)
    val controller = TimedController(app.tileGrid)

    controller.add(KeyCode.LEFT) {
        player.rotate(true)
    }

    controller.add(KeyCode.RIGHT) {
        player.rotate((false))
    }

    controller.add(KeyCode.UP) {
        player.walk(true)
    }

    controller.add(KeyCode.DOWN) {
        player.walk(false)
    }

    // XXX:
    var playing = true
    controller.add(KeyCode.KEY_Q) {
        playing = false
    }

    while (playing) {
        canvas.draw {
            val side = third

            // TODO: redesign & move to its own class
            Tile.createCharacterTile(
                first.texture,
                StyleSet.newBuilder().withBackgroundColor(TileColor.defaultBackgroundColor())
                    .withForegroundColor(
                        TileColor.fromString(
                            when (first.texture) {
                                '@' -> if (side) "#33ff00" else "#11aa00"
                                '#' -> if (side) "#ff0000" else "#990000"
                                else -> if (side) "#00fffc" else "#0099fc"
                            }
                        )
                    ).build()
            )
        }

        val currentTimeStamp = System.nanoTime()
        deltaTimeSeconds = (currentTimeStamp - nanoTimeStamp) / 1e9
        frameRate = 1.0 / deltaTimeSeconds
        nanoTimeStamp = currentTimeStamp

        controller.process()
        // XXX: this assumes the machine is fast enough to have 60 cycles per second. I mean, it should...
        Thread.sleep(((1.0 / 60.0) * 1000.0).toLong())
    }

    // XXX:
    exitProcess(0)
}