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
        kotlin("plugin.serialization") version (extra["kotlin.version"] as String) apply false
        id("com.android.application") version (extra["agp.version"] as String) apply false
        id("com.android.library") version (extra["agp.version"] as String) apply false
        id("org.jetbrains.compose") version (extra["compose.version"] as String) apply false
    }

    dependencyResolutionManagement {
        versionCatalogs {
            create("libs") {
                version("kotlin", extra["kotlin.version"] as String)
                version("coroutines", "1.7.1")
                version("compose", extra["compose.version"] as String)
                version("composeMaterial3", "1.1.1")
                version("decompose", "2.0.0-alpha-02")
                version("kodein", "7.19.0")
                version("ktor", "2.3.1")

                library(
                    "clikt",
                    "com.github.ajalt.clikt",
                    "clikt"
                ).version("3.5.2")
                library(
                    "compose-material3-windowSize",
                    "dev.chrisbanes.material3:material3-window-size-class-multiplatform:0.2.0"
                )
                library(
                    "decompose",
                    "com.arkivanov.decompose",
                    "decompose"
                ).versionRef("decompose")
                library(
                    "decompose-jetbrains",
                    "com.arkivanov.decompose",
                    "extensions-compose-jetbrains"
                ).versionRef("decompose")
                library(
                    "kimchi",
                    "com.inkapplications.kimchi:core:2.0.3"
                )
                library(
                    "kodein",
                    "org.kodein.di",
                    "kodein-di"
                ).versionRef("kodein")
                library(
                    "kodein-android-core",
                    "org.kodein.di",
                    "kodein-di-framework-android-core"
                ).versionRef("kodein")
                library(
                    "klock",
                    "com.soywiz.korlibs.klock:klock:4.0.2"
                )
                library(
                    "kotlin-coroutines-core",
                    "org.jetbrains.kotlinx",
                    "kotlinx-coroutines-core"
                ).versionRef("coroutines")
                library(
                    "kotlin-coroutines-android",
                    "org.jetbrains.kotlinx",
                    "kotlinx-coroutines-android"
                ).versionRef("coroutines")
                library(
                    "kotlin-coroutines-javafx",
                    "org.jetbrains.kotlinx",
                    "kotlinx-coroutines-javafx"
                ).versionRef("coroutines")
                library(
                    "ktor-client-core",
                    "io.ktor",
                    "ktor-client-core"
                ).versionRef("ktor")
                library(
                    "ktor-client-cio",
                    "io.ktor",
                    "ktor-client-cio"
                ).versionRef("ktor")
                library(
                    "kotlin-serialization-json",
                    "org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0"
                )
            }
        }
    }
}

rootProject.name = "lavalamp"

include(
    // app frontends for creating needed things per platform
    ":frontend:android",
    ":frontend:desktop",
    ":frontend:terminal",

    // shared frontend
    ":frontend:di",
    ":frontend:common",
    ":frontend:navigation:core",
    ":frontend:navigation:impl",
    ":frontend:resources",

    // compose frontend things
    ":frontend:compose:core",

    // frontend uis the actual impls of things like "screens"
    ":frontend:ui:home:core",
    ":frontend:ui:home:impl",
    ":frontend:ui:createProject:core",
    ":frontend:ui:createProject:impl",
    ":frontend:ui:projectDetails:core",
    ":frontend:ui:projectDetails:impl",

    //backend
    ":backend:api",

    // shared across everything
    ":common:analytics",
    ":common:appwrite",
    ":common:logging",
    ":common:sdk",

    // other
    ":imageParser",
)
