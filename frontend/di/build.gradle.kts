plugins {
    `multiplatform-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kodein)
                api(libs.decompose.jetbrains)

                implementation(module = ProjectGradleModule.BackendApi)
                implementation(module = ProjectGradleModule.FrontendCommon)
                implementation(module = ProjectGradleModule.FrontendNavigationImpl)
                implementation(module = ProjectGradleModule.FrontendUiHomeImpl)
                implementation(module = ProjectGradleModule.FrontendUiCreateProjectImpl)
                implementation(module = ProjectGradleModule.FrontendUiProjectDetailsImpl)

                implementation(libs.kotlin.coroutines.core)
            }
        }
        val androidMain by getting {
            dependencies {
                api(libs.kodein.android.core)

                implementation(libs.appcompat)
            }
        }
    }
}
