import gradle.kotlin.dsl.accessors._9a33c1b87debd34fc7734fd23358d5b5.android
import org.gradle.accessors.dm.LibrariesForLibs

val libs = the<LibrariesForLibs>()

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("multiplatform-lib")
    id("org.jetbrains.compose")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
}
