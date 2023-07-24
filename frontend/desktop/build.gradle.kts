import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "com.weesnerDevelopment.lavalamp.desktop"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":common:logging"))
    implementation(project(":frontend:di"))
    implementation(project(":frontend:common"))
    implementation(project(":frontend:compose:core"))
    implementation(project(":frontend:navigation:core"))
    implementation(project(":frontend:navigation:impl"))
    implementation(compose.desktop.currentOs)
}

compose.desktop {
    application {
        mainClass = "com.weesnerDevelopment.lavalamp.desktop.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "lavalamp"
            packageVersion = version.toString()

            macOS {
                setDockNameSameAsPackageName = true
            }
        }
    }
}
