plugins {
    `multiplatform-lib`
    id("kotlin-parcelize")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.decompose)
                api(libs.decompose.jetbrains)

                implementation(module = ProjectGradleModule.CommonSdk)
                implementation(module = ProjectGradleModule.BackendApi)
                implementation(module = ProjectGradleModule.FrontendCommon)
                implementation(module = ProjectGradleModule.FrontendUiHomeCore)
                implementation(module = ProjectGradleModule.FrontendUiCreateProjectCore)
                implementation(module = ProjectGradleModule.FrontendUiProjectDetailsCore)

                implementation(libs.kotlin.coroutines.core)
            }
        }
    }
}
