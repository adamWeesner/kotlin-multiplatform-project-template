plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlin-parcelize")
}

group = "com.weesnerDevelopment.lavalamp.frontend.navigation"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.decompose)
                api(libs.decompose.jetbrains)
                implementation(libs.kotlin.coroutines.core)
                implementation(project(":common:sdk"))
                implementation(project(":backend:api"))
                implementation(project(":frontend:ui:home"))
                implementation(project(":frontend:ui:createProject"))
                implementation(project(":frontend:ui:projectDetails"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidUnitTest by getting {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        val desktopMain by getting
        val desktopTest by getting
    }
}

android {
    namespace = group.toString()
    compileSdk = 33
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
