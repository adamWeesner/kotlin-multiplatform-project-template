package com.weesnerDevelopment.lavalamp.frontend.resources

import java.util.UUID

actual fun randomUUID(): String {
    return UUID.randomUUID().toString()
}
