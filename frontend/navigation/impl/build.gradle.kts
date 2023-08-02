plugins {
    `multiplatform-compose-lib`
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.decompose)
                api(libs.decompose.jetbrains)

                implementation(module = ProjectGradleModule.CommonLogging)
                implementation(module = ProjectGradleModule.CommonSdk)
                implementation(module = ProjectGradleModule.FrontendCommon)
                implementation(module = ProjectGradleModule.FrontendComposeCore)
                implementation(module = ProjectGradleModule.FrontendNavigationCore)
                implementation(module = ProjectGradleModule.FrontendUiHomeCore)
                implementation(module = ProjectGradleModule.FrontendUiHomeImpl)
                implementation(module = ProjectGradleModule.FrontendUiCreateProjectCore)
                implementation(module = ProjectGradleModule.FrontendUiCreateProjectImpl)
                implementation(module = ProjectGradleModule.FrontendUiProjectDetailsCore)
                implementation(module = ProjectGradleModule.FrontendUiProjectDetailsImpl)

                implementation(libs.kodein)
                implementation(libs.kotlin.coroutines.core)
            }
        }
    }
}
