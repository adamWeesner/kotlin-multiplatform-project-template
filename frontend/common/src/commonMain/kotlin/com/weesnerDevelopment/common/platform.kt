package com.weesnerDevelopment.common

enum class Platform {
    Desktop,
    Terminal,
    Android,
}

expect fun getPlatformName(): String