plugins {
    `multiplatform-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kodein)

                implementation(module = ProjectGradleModule.CommonSdk)
                implementation(module = ProjectGradleModule.CommonAppwrite)
                implementation(module = ProjectGradleModule.CommonLogging)
            }
        }
    }
}
