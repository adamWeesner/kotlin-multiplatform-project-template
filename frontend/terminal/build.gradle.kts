plugins {
    kotlin("jvm")
    application
}

group = "com.weesnerDevelopment.lavalamp.terminal"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("com.weesnerDevelopment.lavalamp.terminal.MainKt")
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.javafx)
    implementation(libs.clikt)
    implementation(project(":common:logging"))
    implementation(project(":frontend:common"))
    implementation(project(":frontend:di"))
    implementation(project(":frontend:navigation:core"))
    implementation(project(":frontend:navigation:impl"))
    implementation(project(":frontend:ui:home:core"))
    implementation(project(":frontend:ui:home:impl"))
    implementation(project(":frontend:ui:createProject:core"))
    implementation(project(":frontend:ui:createProject:impl"))
    implementation(project(":frontend:ui:projectDetails:core"))
    implementation(project(":frontend:ui:projectDetails:impl"))
}
