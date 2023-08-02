plugins {
    `multiplatform-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(module = ProjectGradleModule.BackendApi)
                api(module = ProjectGradleModule.CommonSdk)
            }
        }
    }
}
