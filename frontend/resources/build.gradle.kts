plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "com.weesnerDevelopment.lavalamp.frontend.resources"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
        jvmToolchain(17)
    }
    sourceSets {
        val commonMain by getting
        val androidMain by getting
        val desktopMain by getting
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}
