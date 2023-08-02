@file:Suppress("UNCHECKED_CAST")
package io.appwrite

import io.appwrite.exceptions.AppwriteException
import io.appwrite.extensions.fromJson
import io.appwrite.extensions.toJson
import io.appwrite.models.InputFile
import io.appwrite.models.UploadProgress
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import java.security.cert.X509Certificate
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.coroutines.CoroutineContext

class Client @JvmOverloads constructor(
    var endPoint: String = "https://HOSTNAME/v1",
    private var selfSigned: Boolean = false
) : CoroutineScope {

    companion object {
        const val CHUNK_SIZE = 5 * 1024 * 1024 // 5MB
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val job = Job()

    lateinit var client: HttpClient

    private val headers: MutableMap<String, String> = mutableMapOf(
        "content-type" to "application/json",
        "x-sdk-name" to "Kotlin",
        "x-sdk-platform" to "server",
        "x-sdk-language" to "kotlin",
        "x-sdk-version" to "2.0.0",
        "x-appwrite-response-format" to "1.0.0"
    )

    val config: MutableMap<String, String> = mutableMapOf()

    init {
        setSelfSigned(selfSigned)
    }

    /**
     * Set Project
     *
     * Your project ID
     *
     * @param {string} project
     *
     * @return this
     */
    fun setProject(value: String): Client {
        config["project"] = value
        addHeader("x-appwrite-project", value)
        return this
    }

    /**
     * Set Key
     *
     * Your secret API key
     *
     * @param {string} key
     *
     * @return this
     */
    fun setKey(value: String): Client {
        config["key"] = value
        addHeader("x-appwrite-key", value)
        return this
    }

    /**
     * Set JWT
     *
     * Your secret JSON Web Token
     *
     * @param {string} jwt
     *
     * @return this
     */
    fun setJWT(value: String): Client {
        config["jWT"] = value
        addHeader("x-appwrite-jwt", value)
        return this
    }

    /**
     * Set Locale
     *
     * @param {string} locale
     *
     * @return this
     */
    fun setLocale(value: String): Client {
        config["locale"] = value
        addHeader("x-appwrite-locale", value)
        return this
    }

    /**
     * Set self Signed
     *
     * @param status
     *
     * @return this
     */
    fun setSelfSigned(status: Boolean): Client {
        selfSigned = status

        if (!selfSigned) {
            client = HttpClient(CIO) {
                expectSuccess = false
            }
            return this
        }

        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                @Suppress("CustomX509TrustManager")
                object : X509TrustManager {
                    @Suppress("TrustAllX509TrustManager")
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Suppress("TrustAllX509TrustManager")
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            client = HttpClient(CIO) {
                expectSuccess = false

                engine {
                    https {
                        trustManager = trustAllCerts[0]
                    }
                }
            }
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

        return this
    }

    /**
     * Set endpoint.
     *
     * @param endpoint
     *
     * @return this
     */
    fun setEndpoint(endPoint: String): Client {
        this.endPoint = endPoint
        return this
    }

    /**
     * Add Header
     *
     * @param key
     * @param value
     *
     * @return this
     */
    fun addHeader(key: String, value: String): Client {
        headers[key] = value
        return this
    }

    /**
     * Send the HTTP request
     *
     * @param method
     * @param path
     * @param headers
     * @param params
     *
     * @return [T]
     */
    @Throws(AppwriteException::class)
    suspend fun <T> call(
        method: String,
        path: String,
        headers: Map<String, String> = mapOf(),
        params: Map<String, Any?> = mapOf(),
        responseType: Class<T>,
        converter: ((Any) -> T)? = null
    ): T {
        val filteredParams = params.filterValues { it != null }

        val response = client.request {
            headers {
                headers.forEach {
                    addHeader(it.key, it.value)
                }
            }

            this.method = HttpMethod.parse(method)
            url(endPoint + path)

            if (method == HttpMethod.Get.value) {
                filteredParams.forEach {
                    when (it.value) {
                        null -> {
                            return@forEach
                        }

                        is List<*> -> {
                            val list = it.value as List<*>
                            for (index in list.indices) {
                                parameter("${it.key}[]", list[index].toString())
                            }
                        }

                        else -> {
                            parameter(it.key, it.value.toString())
                        }
                    }
                }
            } else {
                if (headers["content-type"] == ContentType.MultiPart.FormData.contentType) {
                    setBody(
                        MultiPartFormDataContent(
                            formData {
                                filteredParams.forEach {
                                    when {
                                        it.key == "file" -> {
                                            append(it.value as FormPart<Any>)
                                        }

                                        it.value is List<*> -> {
                                            val list = it.value as List<*>
                                            for (index in list.indices) {
                                                append("${it.key}[]", list[index].toString())
                                            }
                                        }

                                        else -> {
                                            append(it.key, it.value.toString())
                                        }
                                    }
                                }
                            },
                        )
                    )
                } else {
                    contentType(ContentType.Application.Json)
                    setBody(filteredParams.toJson())
                }
            }
        }

        val body = response.bodyAsText()

        if (!response.status.isSuccess()) {
            if (response.headers["content-type"]?.contains("application/json") == true) {
                val map = body.fromJson<Map<String, Any>>()
                throw AppwriteException(
                    map["message"] as? String ?: "",
                    (map["code"] as Number).toInt(),
                    map["type"] as? String ?: "",
                    body
                )
            } else {
                throw AppwriteException(body, response.status.value)
            }
        }

        when {
            responseType == Boolean::class.java -> {
                return true as T
            }

            responseType == ByteArray::class.java -> {
                return response.readBytes() as T
            }

            response.bodyAsText().isEmpty() -> {
                return true as T
            }

            else -> {
                val bodyAsJson = response.bodyAsText().fromJson<Map<String, Any>>()
                return converter?.invoke(bodyAsJson) ?: bodyAsJson as T
            }
        }
    }

    /**
     * Upload a file in chunks
     *
     * @param path
     * @param headers
     * @param params
     *
     * @return [T]
     */
    @Throws(AppwriteException::class)
    suspend fun <T> chunkedUpload(
        path: String,
        headers: MutableMap<String, String>,
        params: MutableMap<String, Any?>,
        responseType: Class<T>,
        converter: ((Any) -> T),
        paramName: String,
        idParamName: String? = null,
        onProgress: ((UploadProgress) -> Unit)? = null,
    ): T {
        val file = MultiplatformFile()

        val input = params[paramName] as InputFile
        val size: Long = when (input.sourceType) {
            "path", "file" -> {
                file.fetch(input.path)
                file.fileSize
            }

            "bytes" -> {
                (input.data as ByteArray).size.toLong()
            }

            else -> throw UnsupportedOperationException()
        }

        if (size < CHUNK_SIZE) {
            when (input.sourceType) {
                "file", "path" -> {
                    val data = file.read()
                    params[paramName] = MultiPartFormDataContent(
                        formData {
                            append(input.filename, data)
                        }
                    )
                }

                "bytes" -> {
                    val data: ByteArray = input.data as ByteArray

                    params[paramName] = MultiPartFormDataContent(
                        formData {
                            append(input.filename, data, Headers.build {
                                append(HttpHeaders.ContentType, input.mimeType)
                                append(
                                    HttpHeaders.ContentDisposition,
                                    "filename=\"${input.filename}\""
                                )
                            })
                        }
                    )
                }// parse byte array to media type
                else -> throw UnsupportedOperationException()
            }
            return call(
                method = "POST",
                path,
                headers,
                params,
                responseType,
                converter
            )
        }

        val buffer = ByteArray(CHUNK_SIZE)
        var offset = 0L
        var result: Map<*, *>? = null

        if (idParamName?.isNotEmpty() == true && params[idParamName] != "unique()") {
            // Make a request to check if a file already exists
            val current = call(
                method = "GET",
                path = "$path/${params[idParamName]}",
                headers = headers,
                params = emptyMap(),
                responseType = Map::class.java,
            )
            val chunksUploaded = current["chunksUploaded"] as Long
            offset = (chunksUploaded * CHUNK_SIZE).coerceAtMost(size)
        }

        while (offset < size) {
            when (input.sourceType) {
                "file", "path" -> {
                    file.seekAndWriteIntoBuffer(offset, buffer)
                }

                "bytes" -> {
                    val end = if (offset + CHUNK_SIZE < size) {
                        offset + CHUNK_SIZE
                    } else {
                        size - 1
                    }
                    (input.data as ByteArray).copyInto(
                        buffer,
                        startIndex = offset.toInt(),
                        endIndex = end.toInt()
                    )
                }

                else -> throw UnsupportedOperationException()
            }

            params[paramName] = MultiPartFormDataContent(
                formData {
                    append(input.filename, buffer, Headers.build {
                        append(HttpHeaders.ContentType, input.mimeType)
                        append(HttpHeaders.ContentDisposition, "filename=\"${input.filename}\"")
                    })
                }
            )

            headers["Content-Range"] =
                "bytes $offset-${((offset + CHUNK_SIZE) - 1).coerceAtMost(size)}/$size"

            result = call(
                method = "POST",
                path,
                headers,
                params,
                responseType = Map::class.java
            )

            offset += CHUNK_SIZE
            headers["x-appwrite-id"] = result["\$id"].toString()
            onProgress?.invoke(
                UploadProgress(
                    id = result["\$id"].toString(),
                    progress = offset.coerceAtMost(size).toDouble() / size * 100,
                    sizeUploaded = offset.coerceAtMost(size),
                    chunksTotal = result["chunksTotal"].toString().toInt(),
                    chunksUploaded = result["chunksUploaded"].toString().toInt(),
                )
            )
        }

        return converter(result as Map<String, Any>)
    }
}
