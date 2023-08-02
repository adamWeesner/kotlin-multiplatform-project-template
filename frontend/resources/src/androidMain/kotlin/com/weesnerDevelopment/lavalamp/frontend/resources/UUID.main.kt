package com.weesnerDevelopment.lavalamp.frontend.resources

import java.util.*

actual fun randomUUID(): String {
    return UUID.randomUUID().toString()
}
