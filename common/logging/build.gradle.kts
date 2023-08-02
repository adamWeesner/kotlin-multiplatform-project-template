plugins {
    `multiplatform-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kimchi)
            }
        }
    }
}
