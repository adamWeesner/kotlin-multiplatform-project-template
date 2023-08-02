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
                implementation(module = ProjectGradleModule.FrontendNavigationCore)
                implementation(module = ProjectGradleModule.FrontendUiProjectDetailsCore)

                implementation(libs.decompose)
                implementation(libs.decompose.jetbrains)
                implementation(libs.kotlin.coroutines.core)
                implementation(libs.kodein)
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
    }
}
