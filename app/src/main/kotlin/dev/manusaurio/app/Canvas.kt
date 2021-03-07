package dev.manusaurio.app

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
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

        app.tileGrid.tileset = CP437TilesetResources.zilk16x16()

        app.tileGrid.plotLine(0, 0, 10, 10);
        app.tileGrid.plotLine(0, 0, 5, 10);
        app.tileGrid.plotLine(0, 0, 15, 10);
        app.tileGrid.plotLine(0, 0, 0, 10);
        app.tileGrid.plotLine(0, 0, 10, 0);

        (app.tileGrid.width - 1).let { w ->
            app.tileGrid.plotLine(w, w, w, 0);
            app.tileGrid.plotLine(w, w, 0, w);
            app.tileGrid.plotLine(w, w, w/2, w/2);
        }
    }
}