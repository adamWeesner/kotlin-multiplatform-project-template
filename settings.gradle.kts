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
                version("compose", extra["compose.version"] as String)
                version("decompose", "2.0.0-alpha-02")
                version("kodein", "7.19.0")

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
                    "clikt",
                    "com.github.ajalt.clikt",
                    "clikt"
                ).version("3.5.2")
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
                    "kodein",
                    "org.kodein.di",
                    "kodein-di"
                ).versionRef("kodein")
                library(
                    "kodein-android-core",
                    "org.kodein.di",
                    "kodein-di-framework-android-core"
                ).versionRef("kodein")
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
    ":frontend:navigation",
    ":frontend:resources",

    // compose frontend things
    ":frontend:compose:core",

    // frontend uis the actual impls of things like "screens"
    ":frontend:ui:home",
    ":frontend:ui:createProject",
    ":frontend:ui:projectDetails",

    //backend
    ":backend:api",

    // shared across everything
    ":common:sdk",

    // other
    ":imageParser",
)
