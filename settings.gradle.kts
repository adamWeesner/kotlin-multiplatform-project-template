pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("multiplatform") version (extra["kotlin.version"] as String) apply false
        kotlin("android") version (extra["kotlin.version"] as String) apply false
        id("com.android.application") version (extra["agp.version"] as String) apply false
        id("com.android.library") version (extra["agp.version"] as String) apply false
        id("org.jetbrains.compose") version (extra["compose.version"] as String) apply false
    }

    dependencyResolutionManagement {
        versionCatalogs {
            create("libs") {
                version("kotlin", extra["kotlin.version"] as String)
                version("coroutines", "1.7.1")

                library(
                    "kotlin-coroutines-core",
                    "org.jetbrains.kotlinx",
                    "kotlinx-coroutines-core"
                ).versionRef("coroutines")
                library(
                    "clikt",
                    "com.github.ajalt.clikt",
                    "clikt"
                ).version("3.5.2")
            }
        }
    }
}

rootProject.name = "lavalamp"

include(
    ":frontend:android",
    ":frontend:desktop",
    ":frontend:terminal",
    ":frontend:common",

    ":backend:api",

    ":common:sdk"
)
