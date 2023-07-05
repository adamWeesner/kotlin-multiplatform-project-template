package io.appwrite

import java.io.File
import java.io.RandomAccessFile

internal actual class MultiplatformFile {
    private var file: RandomAccessFile? = null
    private var filePath: String? = null

    actual val fileSize: Long
        get() = file?.length() ?: -1

    actual fun fetch(path: String) {
        filePath = path
        file = RandomAccessFile(path, "r")
    }

    actual fun read(): String {
        return filePath?.let {
            File(it).readText()
        } ?: ""
    }

    actual fun seekAndWriteIntoBuffer(offset: Long, buffer: ByteArray) {
        // sets initial offset of the file to start reading from
        file!!.seek(offset)
        // writes into buffer
        file!!.read(buffer)
    }
}
