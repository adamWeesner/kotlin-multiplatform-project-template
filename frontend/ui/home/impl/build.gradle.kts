plugins {
    `multiplatform-compose-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(module = ProjectGradleModule.BackendApi)
                implementation(module = ProjectGradleModule.CommonLogging)
                implementation(module = ProjectGradleModule.FrontendCommon)
                implementation(module = ProjectGradleModule.FrontendComposeCore)
                implementation(module = ProjectGradleModule.FrontendUiHomeCore)
                implementation(module = ProjectGradleModule.FrontendNavigationCore)

                implementation(libs.decompose)
                implementation(libs.decompose.jetbrains)
                implementation(libs.kotlin.coroutines.core)
                implementation(libs.kodein)
            }
        }
    }
}
