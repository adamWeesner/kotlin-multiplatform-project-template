import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

java {
    sourceCompatibility = JavaVersion.valueOf(libs.versions.java.get())
    targetCompatibility = JavaVersion.valueOf(libs.versions.java.get())
}

dependencies {
    implementation(module = ProjectGradleModule.CommonLogging)
    implementation(module = ProjectGradleModule.FrontendDi)
    implementation(module = ProjectGradleModule.FrontendCommon)
    implementation(module = ProjectGradleModule.FrontendComposeCore)
    implementation(module = ProjectGradleModule.FrontendNavigationCore)
    implementation(module = ProjectGradleModule.FrontendNavigationImpl)

    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.weesnerDevelopment.lavalamp.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "lavalamp"
            version = "1.0.0"
            packageVersion = version.toString()

            macOS {
                setDockNameSameAsPackageName = true
            }

            val path = project.layout.projectDirectory.dir("../../resources/build/generated/sources/desktopMain/resources")
            appResourcesRootDir.set(path)
        }
    }
}
