package cash.z.ecc.android.random

import kotlinx.cinterop.addressOf
import kotlinx.cinterop.convert
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import platform.posix.O_RDONLY
import platform.posix.open
import platform.posix.read

actual class SecureRandom {
    actual fun nextBytes(bytes: ByteArray) = memScoped {
        val randomData = open("/dev/urandom", O_RDONLY)
        if (randomData < 0) {
            throw Error("Could not open /dev/urandom")
        } else {
            val result = bytes.usePinned {
                read(randomData, it.addressOf(0), bytes.size.convert())
            }
            if (result < 0) {
                throw Error("Could not get random number from /dev/urandom")
            }
        }
    }
}