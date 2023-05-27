plugins {
    kotlin("jvm")
}

group = "com.weesnerDevelopment.lavalamp.terminal"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.javafx)
    implementation(libs.clikt)
    implementation(project(":frontend:common"))
    implementation(project(":frontend:navigation"))
    implementation(project(":frontend:ui:home"))
    implementation(project(":frontend:ui:createProject"))
    implementation(project(":frontend:ui:projectDetails"))
}