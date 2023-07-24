package com.weesnerDevelopment.lavalamp.resources

import java.util.*

actual fun randomUUID(): String {
    return UUID.randomUUID().toString()
}
