package jslibs

import org.khronos.webgl.ArrayBufferView

external interface Crypto {
    fun <T : ArrayBufferView> getRandomValues(array: T): T
}