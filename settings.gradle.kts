pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform") version(extra["kotlin.version"] as String) apply false
        kotlin("android") version(extra["kotlin.version"] as String) apply false
        id("com.android.application") version(extra["agp.version"] as String) apply false
        id("com.android.library") version(extra["agp.version"] as String) apply false
        id("org.jetbrains.compose") version(extra["compose.version"] as String) apply false
    }
}

rootProject.name = "lavalamp"

include(
    ":frontend:android",
    ":frontend:desktop",
    ":frontend:terminal",
    ":frontend:common",

    ":common:sdk"
)
