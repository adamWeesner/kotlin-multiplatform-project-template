plugins {
    kotlin("jvm")
}

group = "com.weesnerDevelopment.lavalamp"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.clikt)
    implementation(project(":frontend:common"))
}