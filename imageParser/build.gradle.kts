plugins {
    kotlin("jvm")
    application
}

group = "com.weesnerDevelopment.lavalamp.imageParser"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
}

application {
    mainClass.set("com.weesnerDevelopment.lavalamp.imageParser.ImageParser.kt")
}
