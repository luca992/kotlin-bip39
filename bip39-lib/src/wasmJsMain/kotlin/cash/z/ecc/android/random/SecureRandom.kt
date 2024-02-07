package cash.z.ecc.android.random

import jslibs.Crypto
import org.khronos.webgl.Uint8Array
import org.khronos.webgl.get

// https://github.com/whyoleg/cryptography-kotlin/blob/d524143a0719e6926b0ae190977a7341673fa718/cryptography-random/src/wasmJsMain/kotlin/CryptographyRandom.wasmJs.kt#L40-L54
//language=JavaScript
private fun getCrypto(): Crypto {
    js(
        code = """
    
        var isNodeJs = typeof process !== 'undefined' && process.versions != null && process.versions.node != null
        if (isNodeJs) {
            return (eval('require')('node:crypto').webcrypto);
        } else {
            return (window ? (window.crypto ? window.crypto : window.msCrypto) : self.crypto);
        }
    
               """
    )
}

actual class SecureRandom {

    actual fun nextBytes(bytes: ByteArray) {
        val uint8bytes = Uint8Array(bytes.size)
        getCrypto().getRandomValues(uint8bytes)
        repeat(bytes.size) { i ->
            bytes[i] = uint8bytes[i]
        }
    }
}
