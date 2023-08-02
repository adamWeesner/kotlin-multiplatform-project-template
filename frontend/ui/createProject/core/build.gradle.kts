plugins {
    `multiplatform-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(module = ProjectGradleModule.FrontendCommon)
                implementation(module = ProjectGradleModule.FrontendComposeCore)

                implementation(libs.decompose)
            }
        }
    }
}
