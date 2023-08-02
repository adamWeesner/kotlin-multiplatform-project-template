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
                implementation(module = ProjectGradleModule.FrontendResources)
                implementation(module = ProjectGradleModule.FrontendUiCreateProjectCore)

                implementation(libs.decompose)
                implementation(libs.decompose.jetbrains)
                implementation(libs.klock)
                implementation(libs.kotlin.coroutines.core)
                implementation(libs.kodein)
            }
        }
    }
}
