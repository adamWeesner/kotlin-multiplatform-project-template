plugins {
    `multiplatform-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(module = ProjectGradleModule.CommonSdk)

                implementation(libs.decompose)
            }
        }
    }
}
