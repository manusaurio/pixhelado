package dev.manusaurio.app

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import dev.manusaurio.app.Projector.Companion.createProjector
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig

class Canvas {
    fun draw() {
        val app = LibgdxApplications.startApplication(
            AppConfig.newBuilder().withSize(32, 32).build(),
            LwjglApplicationConfiguration().apply {
                // this is necessary in i3wm:
                resizable = false
            }
        )

        app.tileGrid.tileset = CP437TilesetResources.rogueYun16x16()

        val projector = app.tileGrid.createProjector()

        (app.tileGrid.width - 1).let { w ->
            projector.run {
                plotLine(0, 0, w, 0)
                plotLine(w, 0, w, w)
                plotLine(w, w, 0, w)
                plotLine(0, w, 0, 0)

                plotLine(0, 0, w/2, w)
                plotLine(w/2, w+1, w, 0)

                plotLine(55, w/2,-55, w/2)

                plotLine(55, w/2 + 2, -55, w/2 + 2)
            }


        }
    }
}