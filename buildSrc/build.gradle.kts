@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    `kotlin-dsl`
    alias(libs.plugins.compose)
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(libs.agp)
    implementation(libs.gradle.plugin.kotlin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

tasks.named("compileKotlin").get().dependsOn("generateProjectModules")

sourceSets {
    getByName("main").java.srcDirs("${project.buildDir}/generated/sources/generateProjectModules/main/kotlin")
}

tasks.register<ProjectModuleGeneratorTask>("generateProjectModules") {
    // replace this with your base package name
    basePackage = "com.weesnerDevelopment.lavalamp"

    input.set(layout.projectDirectory.file("../settings.gradle.kts"))

    val outputFile = layout
        .buildDirectory
        .file(
            "generated/sources/$name/main/kotlin/${
                basePackage.replace(
                    ".",
                    "/"
                )
            }/ProjectGradleModule.kt"
        )

    output.set(outputFile)
}

abstract class ProjectModuleGeneratorTask : DefaultTask() {
    @get:Input
    abstract var basePackage: String

    @get:InputFile
    abstract val input: RegularFileProperty

    @get:OutputFile
    abstract val output: RegularFileProperty

    @TaskAction
    fun generateEnum() {
        val inputFile = input.get().asFile
        val outputFile = output.get().asFile

        if (!outputFile.exists()) {
            println("output file didn't exist; creating...")
            outputFile.parentFile.mkdirs()
            outputFile.writeText("")
        }

        val gradleModules = inputFile.readLines().filter {
            it.trim().startsWith("include") || it.trim().startsWith("""":""")
        }.mapNotNull {
            val filtered = it
                .replace("include", "")
                .replace(Regex("""["(),]"""), "")
                .trim()

            if (filtered.isBlank()) {
                null
            } else {
                val enumName = filtered
                    .split(":", "_", "-")
                    .joinToString("") { it.capitalize() }

                """/**
                 * Gradle Module - $filtered
                 */
                ${enumName}(project = "$filtered")"""
            }
        }

        val modulesEnum = """
            import org.gradle.kotlin.dsl.DependencyHandlerScope
            import org.gradle.kotlin.dsl.project
            import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler
            
            /**
             * The projects gradle modules in a type safe way built from the settings.gradle.kts file. This meant to help with 
             * type-safety and allow for easier adding of gradle modules without needing to worry about typos ;)
             * 
             * This now works with Kotlin Multiplatform as well!
             *
             * There are many ways to use this, for your gradle module that for examples sake is named ":businessRules" you can do 
             * any of the following:
             * ```kotlin
             * implementation|api(project(ProjectGradleModule.BusinessRules.project))
             * ```
             * ```kotlin
             * implementation|api(ProjectGradleModule.BusinessRules)
             * ```
             * ```kotlin
             * // this does not apply to KMM or KMP modules
             * testImplementation|testApi(project(ProjectGradleModule.BusinessRules.project))
             * ```
             * ```kotlin
             * // this does not apply to KMM or KMP modules
             * testImplementation|testApi(ProjectGradleModule.BusinessRules)
             * ```
             * 
             * @param project The name of the gradle module, to be used for something like `implementation(project(":businessRules"))` 
             * is replaced with one of the helper functions noted above in the [ProjectGradleModule]!
             */
            enum class ProjectGradleModule(val project: String) {
                ${gradleModules.joinToString(",\n                ")}
            }
            
            
            /**
             * Special version of implementation that only takes the [module] and adds it as a 
             * `implementation(project({[module.project]}))`
             */
            fun KotlinDependencyHandler.implementation(module: ProjectGradleModule) {
                implementation(project(module.project))
            }

            /**
             * Special version of implementation that only takes the [module] and adds it as a 
             * `implementation(project({[module.project]}))`
             */
            fun DependencyHandlerScope.implementation(module: ProjectGradleModule) {
                add("implementation", project(module.project))
            }

            /**
             * Special version of api that only takes the [module] and adds it as a
             * `api(project({[module.project]}))`
             */
            fun KotlinDependencyHandler.api(module: ProjectGradleModule) {
                api(project(module.project))
            }

            /**
             * Special version of api that only takes the [module] and adds it as a
             * `api(project({[module.project]}))`
             */
            fun DependencyHandlerScope.api(module: ProjectGradleModule) {
                add("api", project(module.project))
            }

            /**
             * Special version of testImplementation that only takes the [module] and adds it as a
             * `testImplementation(project({[module.project]}))`
             */
            fun DependencyHandlerScope.testImplementation(module: ProjectGradleModule) {
                add("testImplementation", project(module.project))
            }

            /**
             * Special version of testApi that only takes the [module] and adds it as a
             * `testApi(project({[module.project]}))`
             */
            fun DependencyHandlerScope.testApi(module: ProjectGradleModule) {
                add("testApi", project(module.project))
            }

        """.trimIndent()

        if (outputFile.readText() != modulesEnum) {
            println("Data seems to have changed, updating generated file...")
            outputFile.writeText(modulesEnum)
        }
        println("Generated project gradle modules enum at ${outputFile.path}")
    }
}
