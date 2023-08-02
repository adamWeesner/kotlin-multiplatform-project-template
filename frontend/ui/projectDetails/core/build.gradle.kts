plugins {
    `multiplatform-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(module = ProjectGradleModule.FrontendCommon)

                implementation(libs.decompose)
            }
        }
    }
}
