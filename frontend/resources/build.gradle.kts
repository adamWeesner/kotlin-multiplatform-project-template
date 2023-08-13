plugins {
    `multiplatform-lib`
}

kotlin {
    sourceSets {
        val commonMain by getting {
            // gets the generated things and adds them to the app so we can use them everywhere
            kotlin.srcDirs("${project.buildDir}/generated/sources/commonMain/kotlin/com/weesnerDevelopment/lavalamp/frontend/resources")

            dependencies {
                implementation(module = ProjectGradleModule.CommonSdk)
            }
        }
        val desktopMain by getting {
            resources.srcDirs("${project.buildDir}/generated/sources/desktopMain/resources")
        }
    }
}
