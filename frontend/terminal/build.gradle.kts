plugins {
    kotlin("jvm")
    application
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
}

tasks.withType<Zip>() {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

tasks.withType<Tar>() {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

application {
    mainClass.set("com.weesnerDevelopment.lavalamp.terminal.MainKt")
}

dependencies {
    implementation(module = ProjectGradleModule.CommonLogging)
    implementation(module = ProjectGradleModule.FrontendCommon)
    implementation(module = ProjectGradleModule.FrontendDi)
    implementation(module = ProjectGradleModule.FrontendComposeCore)
    implementation(module = ProjectGradleModule.FrontendNavigationCore)
    implementation(module = ProjectGradleModule.FrontendNavigationImpl)
    implementation(module = ProjectGradleModule.FrontendUiHomeCore)
    implementation(module = ProjectGradleModule.FrontendUiHomeImpl)
    implementation(module = ProjectGradleModule.FrontendUiCreateProjectCore)
    implementation(module = ProjectGradleModule.FrontendUiCreateProjectImpl)
    implementation(module = ProjectGradleModule.FrontendUiProjectDetailsCore)
    implementation(module = ProjectGradleModule.FrontendUiProjectDetailsImpl)

    implementation(libs.clikt)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.javafx)
}
