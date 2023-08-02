plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group = "com.weesnerDevelopment.lavalamp.android"
version = "1.0-SNAPSHOT"

dependencies {
    implementation("androidx.activity:activity-compose:1.5.0")

    implementation(module = ProjectGradleModule.CommonLogging)
    implementation(module = ProjectGradleModule.FrontendCommon)
    implementation(module = ProjectGradleModule.FrontendComposeCore)
    implementation(module = ProjectGradleModule.FrontendDi)
    implementation(module = ProjectGradleModule.FrontendNavigationCore)
    implementation(module = ProjectGradleModule.FrontendNavigationImpl)
}

android {
    namespace = "$group"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()
    defaultConfig {
        applicationId = group.toString()
        multiDexEnabled = true
        minSdk = libs.versions.android.sdk.min.get().toInt()
        targetSdk = libs.versions.android.sdk.target.get().toInt()
        versionCode = 1
        versionName = version.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
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
