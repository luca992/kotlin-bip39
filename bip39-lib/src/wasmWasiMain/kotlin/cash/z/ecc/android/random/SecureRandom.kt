package cash.z.ecc.android.random

import kotlin.wasm.WasmImport
import kotlin.wasm.unsafe.UnsafeWasmMemoryApi
import kotlin.wasm.unsafe.withScopedMemoryAllocator


// https://github.com/whyoleg/cryptography-kotlin/blob/d524143a0719e6926b0ae190977a7341673fa718/cryptography-random/src/wasmWasiMain/kotlin/CryptographyRandom.wasmWasi.kt
actual class SecureRandom {

    @OptIn(UnsafeWasmMemoryApi::class)
    actual fun nextBytes(bytes: ByteArray) {
        val size = bytes.size
        withScopedMemoryAllocator { allocator ->
            val pointer = allocator.allocate(size)
            val result = wasiRandomGet(pointer.address.toInt(), size)
            if (result != 0) error("wasi error code: $result")

            repeat(size) {
                bytes[it] = (pointer + it).loadByte()
            }
        }
    }
}

@WasmImport("wasi_snapshot_preview1", "random_get")
private external fun wasiRandomGet(address: Int, size: Int): Int