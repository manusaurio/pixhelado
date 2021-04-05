package dev.manusaurio.app

import org.hexworks.zircon.api.uievent.*
import java.util.EnumMap

class TimedController(val source: UIEventSource) : UIEventSource by source {
    class InputData(var active: Boolean, val effect: () -> Unit)

    private val inputs: MutableMap<KeyCode, InputData> = EnumMap(KeyCode::class.java)

    fun add(keyCode: KeyCode, effect: () -> Unit) {
        inputs[keyCode] = InputData(false, effect)

        handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { e, _ ->
            return@handleKeyboardEvents if (keyCode == e.code) {
                inputs[keyCode]!!.active = true
                UIEventResponse.processed()
            } else {
                UIEventResponse.pass()
            }
        }

        handleKeyboardEvents(KeyboardEventType.KEY_RELEASED) { e, _ ->
            return@handleKeyboardEvents if (keyCode == e.code) {
                inputs[keyCode]!!.active = false
                UIEventResponse.processed()
            } else {
                UIEventResponse.pass()
            }
        }
    }

    // TODO: maybe make it work with a signature `add(vararg inputs: KeyCode, f: UIEventResponse.() -> UIEventResponse)`
    //  it would need a different structure or the effects would be processed multiple times

    fun process() {
        inputs.values.forEach { input ->
            if (input.active) input.effect()
        }
    }
}