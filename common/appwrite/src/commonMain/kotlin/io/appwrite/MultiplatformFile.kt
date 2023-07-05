package io.appwrite

internal expect class MultiplatformFile constructor() {
    val fileSize: Long

    fun fetch(path: String)
    fun read(): String
    fun seekAndWriteIntoBuffer(offset: Long, buffer: ByteArray)
}
