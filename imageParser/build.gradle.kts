plugins {
    kotlin("jvm")
    application
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
}

application {
    mainClass.set("com.weesnerDevelopment.lavalamp.imageParser.ImageParser.kt")
}
