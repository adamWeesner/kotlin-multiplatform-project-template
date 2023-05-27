plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "com.weesnerDevelopment.lavalamp.android"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(project(":frontend:common"))
    implementation(project(":frontend:navigation"))
    implementation("androidx.activity:activity-compose:1.5.0")
}

android {
    namespace = group.toString()
    compileSdk = 33
    defaultConfig {
        applicationId = group.toString()
        multiDexEnabled = true
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = version.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    packagingOptions {
        pickFirst("META-INF/AL2.0")
        pickFirst("META-INF/LGPL2.0")
        pickFirst("META-INF/LGPL2.1")
    }
}
