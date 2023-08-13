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
}

rootProject.name = "lavalamp"

include(
    // app frontends for creating needed things per platform
    ":frontend:android",
    ":frontend:desktop",
    ":frontend:terminal",

    // shared frontend
    ":frontend:common",
    ":frontend:di",
    ":frontend:navigation:core",
    ":frontend:navigation:impl",
    ":frontend:resources",

    // compose frontend things
    ":frontend:compose:core",

    // frontend uis, things like "screens"
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
)
