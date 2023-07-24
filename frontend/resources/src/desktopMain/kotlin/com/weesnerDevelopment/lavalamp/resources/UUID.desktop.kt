package com.weesnerDevelopment.lavalamp.resources

import java.util.UUID

actual fun randomUUID(): String {
    return UUID.randomUUID().toString()
}
