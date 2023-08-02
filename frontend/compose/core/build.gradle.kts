plugins {
    `multiplatform-compose-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(module = ProjectGradleModule.BackendApi)
                api(module = ProjectGradleModule.CommonSdk)
                api(module = ProjectGradleModule.FrontendCommon)
                api(module = ProjectGradleModule.FrontendResources)

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                api(compose.material3)
                api(libs.compose.material3.windowSize)
            }
        }
        val androidMain by getting {
            dependencies {
                api(compose.preview)
                api(libs.appcompat)
                api("androidx.core:core-ktx:1.9.0")
            }
        }
        val desktopMain by getting {
            dependencies {
                api(compose.preview)
            }
        }
    }
}
