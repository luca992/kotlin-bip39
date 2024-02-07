package jslibs

import org.khronos.webgl.ArrayBufferView

external interface WebCrypto {
    fun <T : ArrayBufferView> getRandomValues(array: T): T
}

// For node
@JsModule("crypto")
@JsNonModule
external val webcrypto: WebCrypto

// For browser
external object window {
    val crypto: WebCrypto
}