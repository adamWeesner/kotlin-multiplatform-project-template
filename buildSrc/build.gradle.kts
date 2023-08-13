import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

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

ext {
    // NOTE: KEEP UP TO DATE WITH YOUR APP NAME
    set("appName", "lavalamp")
    // NOTE: KEEP UP TO DATE WITH YOUR GROUP NAME (MINUS APP NAME)
    set("baseAppPath", "com/weesnerDevelopment")
}

tasks.named("compileKotlin").get().dependsOn(
    "generateProjectModules",
    "generateStrings",
    "generateImages"
)

sourceSets {
    getByName("main").java.srcDirs("${project.buildDir}/generated/sources/main/kotlin")
}

tasks.register<StringsGeneratorTask>("generateStrings") {
    val basePath = ext.get("baseAppPath").toString()
    val name = ext.get("appName").toString()
    val path = "$basePath/$name"

    createTask(
        "../frontend/resources/src/commonMain/kotlin/$path/frontend/resources/strings/AllStrings.kt",
        "../../frontend/resources/build/generated/sources/commonMain/kotlin/$path/frontend/resources/strings/Strings.kt"
    )
}

tasks.register<ImageGeneratorTask>("generateImages") {
    val baseAppPath = ext.get("baseAppPath").toString()
    val name = ext.get("appName").toString()
    val path = "$baseAppPath/$name"

    val inputPath = "../images"
    val outputPathResources = "../frontend/resources/build/generated/sources"
    val outputPathCompose = "../frontend/compose/core/build/generated/sources"

    basePackage = path.replace("/", ".")
    basePath = baseAppPath
    appName = name

    input.set(layout.projectDirectory.dir(inputPath))
    outputResources.set(layout.projectDirectory.dir(outputPathResources))
    outputCompose.set(layout.projectDirectory.dir(outputPathCompose))
}

tasks.register<ProjectModuleGeneratorTask>("generateProjectModules") {
    createTask(
        "../settings.gradle.kts",
        "generated/sources/main/kotlin/ProjectGradleModule.kt"
    )
}

fun <T : GeneratorTask> T.createTask(
    inputPath: String,
    outputPath: String
) {
    val baseAppPath = ext.get("baseAppPath").toString()
    val name = ext.get("appName").toString()
    val path = "$baseAppPath/$name"

    basePackage = path.replace("/", ".")

    input.set(layout.projectDirectory.file(inputPath))
    val outputFile = layout.buildDirectory.file(outputPath)
    output.set(outputFile)
}


abstract class GeneratorTask : DefaultTask() {
    @get:Input
    abstract var basePackage: String

    @get:InputFile
    abstract val input: RegularFileProperty

    @get:OutputFile
    abstract val output: RegularFileProperty

    @TaskAction
    abstract fun generate()
}

abstract class ProjectModuleGeneratorTask : GeneratorTask() {
    @TaskAction
    override fun generate() {
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

abstract class StringsGeneratorTask : GeneratorTask() {
    @TaskAction
    override fun generate() {
        val inputFile = input.get().asFile
        val outputFile = output.get().asFile

        val variantPath = File(inputFile.parent, "variant")
        val variants = variantPath.listFiles()?.map {
            val split = it.name.replace(".kt", "").split("-")

            split[0] to split[1]
        } ?: emptyList()

        val variantsToItem = variants.map {
            "country == Country.get(\"${it.second}\") && language == Language.get(\"${it.first}\") -> `${it.first}-${it.second}`"
        }
        val variantImport = variants.map {
            "import $basePackage.frontend.resources.strings.variant.`${it.first}-${it.second}`"
        }

        if (!outputFile.exists()) {
            println("output file for 'Strings.kt' didn't exist; creating...")
            outputFile.parentFile.mkdirs()
            outputFile.writeText("")
        } else {
            println("output file for 'Strings.kt' exists; overriding...")
        }

        val imports = inputFile.readLines().filter {
            it.trim().startsWith("import")
        }.map { it.trim() }

        val strings = inputFile.readLines().filter {
            it.trim().startsWith("val") || it.trim().startsWith("fun")
        }.map {
            val variable = it.trim()

            it.trim().let { name ->
                if (name.startsWith("val")) {
                    val variableName = name.replace("val ", "")
                        .replace(Regex(": \\w+"), "")

                    "override $variable get() = getStrings().$variableName"
                } else {
                    val variableName = name
                        .replace("fun ", "")
                        .replace(Regex(": \\w+"), "")

                    "override $variable = getStrings().$variableName"
                }

            }
        }

        val invalidCombo =
            "throw IllegalArgumentException(\"Country (\$country) and language (\$language) combination currently not supported\")"

        val stringsFileData = """
            package $basePackage.frontend.resources.strings

            ${variantImport.joinToString("\n            ")}
            ${imports.joinToString("\n            ")}
            import java.util.*

            // see the link to know the codes to add more
            // https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes
            object Strings : AllStrings {
                private var locale: Locale = Locale.getDefault()

                fun setLocale(locale: Locale) {
                    Strings.locale = locale
                }
                
                ${strings.joinToString("\n\n                ")}
                
                private fun getStrings(): AllStrings {
                    val country = Country.get(locale.isO3Country)
                    val language = Language.get(locale.isO3Language)

                    return when {
                        ${variantsToItem.joinToString("\n                    ")}

                        else -> {
                            $invalidCombo
                        }
                    }
                }
            }

        """.trimIndent()

        if (outputFile.readText() != stringsFileData) {
            println("'Strings.kt' has changed, updating generated file...")
            outputFile.writeText(stringsFileData)
            println("Generated 'Strings.kt' at ${outputFile.path}")
        } else {
            println("No changes to 'Strings.kt' at ${outputFile.path}")
        }
    }
}

abstract class ImageGeneratorTask : DefaultTask() {
    @get:Input
    abstract var basePackage: String

    @get:Input
    abstract var basePath: String

    @get:Input
    abstract var appName: String

    @get:InputDirectory
    abstract val input: DirectoryProperty

    @get:OutputDirectory
    abstract val outputCompose: DirectoryProperty

    @get:OutputDirectory
    abstract val outputResources: DirectoryProperty

    @TaskAction
    fun generate() {
        val basePath = basePackage.replace(".", "/")

        val inputDir = input.get().asFile
        val outputDirRes = outputResources.get().asFile
        val outputDirCompose = outputCompose.get().asFile

        val fileList = inputDir.listFiles()?.toList() ?: emptyList()

        val fileNames = generateFileNames(fileList)

        // desktop
        copySvgs(outputDirRes, fileList)
        updateImagePainterDesktop(outputDirCompose, fileNames)

        // android
        inputDir.listFiles()?.forEach { parseSvgForAndroid(outputDirRes, it) }
        updateImagePainterAndroid(outputDirCompose, fileNames)

        //common
        generateCommonClasses(outputDirRes, basePath, fileNames)
        updateImagePainterCommon(outputDirCompose, fileNames)
    }


    private fun copySvgs(baseFile: File, files: List<File>) {
        println("starting to copy svgs over...")
        files.forEach {
            println("attempting to copy ${it.name}")
            val new = File(baseFile, "desktopMain/resources/common/${it.name}")
            it.copyTo(new, overwrite = true)
            println("copied file ${it.name} to ${new.path}")
        }
    }

    class Image(
        val name: String,
        val fileName: String
    ) {
        override fun toString(): String {
            return """
                object $name : Image(fileName = "$fileName")
            """.trimIndent()
        }
    }

    private fun generateFileNames(files: List<File>): List<Image> {
        val fileNames = mutableListOf<Image>()
        files.map { it.nameWithoutExtension }.forEach {
            if (it.contains("_")) {
                val name = it.split("_").joinToString("") { part ->
                    part.capitalize()
                }
                fileNames.add(Image(name, it))
            } else {
                val name = it.capitalize()
                fileNames.add(Image(name, it))
            }
        }

        return fileNames
    }

    private fun generateCommonClasses(baseFile: File, basePath: String, fileNames: List<Image>) {
        val classes = """
        package $basePackage.resources
        
        sealed class Image(val fileName: String) {
            ${fileNames.joinToString("\n            ") { it.toString() }}
        }
        
    """.trimIndent()

        val new = File(
            baseFile,
            "commonMain/kotlin/$basePath/frontend/resources/Image.kt"
        )
        new.ensureParentDirsCreated()
        new.createNewFile()
        new.writeText(classes)
        println("Updated $new")
    }

    private fun parseSvgForAndroid(baseFile: File, data: File) {
        val text = data.readText()
        val split = text.split(Regex("<")).filter { it.isNotBlank() }
        val svgData = SvgData()

        split.filter { !it.startsWith("path") }.forEach {
            Regex("""(([\w-]+)="([^"]*)")""").findAll(it).forEach { result ->
                val trimmedResult = result.value.replace("\"", "")
                val svgVariable = SvgVariable.fromString(trimmedResult)

                when (svgVariable) {
                    SvgVariable.Width ->
                        svgData.width = svgVariable.parseData(trimmedResult)

                    SvgVariable.Height ->
                        svgData.height = svgVariable.parseData(trimmedResult)

                    SvgVariable.ViewBox -> {
                        val split = trimmedResult.split(" ")
                        svgData.viewWidth = split[2]
                        svgData.viewHeight = split[3]
                    }

                    SvgVariable.Fill ->
                        svgData.fill = svgVariable.parseData(trimmedResult)

                    else -> { /* not required*/
                    }
                }
            }
        }

        val paths = mutableListOf<PathData>()
        split.filter { it.startsWith("path") }.forEach {
            val pathData = PathData()

            Regex("""(([\w-]+)="([^"]*)")""").findAll(it).forEach { result ->
                val trimmedResult = result.value.replace("\"", "")
                val pathVariable = PathVariable.fromString(trimmedResult)

                when (pathVariable) {
                    PathVariable.Data ->
                        pathData.path = pathVariable.parseData(trimmedResult)

                    PathVariable.Stroke ->
                        pathData.strokeColor = pathVariable.parseData(trimmedResult)

                    PathVariable.StrokeWidth ->
                        pathData.strokeWidth = pathVariable.parseData(trimmedResult)

                    PathVariable.StrokeLineCap ->
                        pathData.strokeLineCap = pathVariable.parseData(trimmedResult)

                    PathVariable.Fill ->
                        pathData.fillColor = pathVariable.parseData(trimmedResult)
                }
            }

            paths.add(pathData)
        }

        svgData.paths = paths

        val new = File(
            baseFile,
            "androidMain/res/drawable/${data.nameWithoutExtension.toLowerCase()}.xml"
        )
        new.ensureParentDirsCreated()
        val newFile = new.createNewFile()

        new.writeText(svgData.toAndroidXml())
        if (newFile) {
            println("added $new")
        } else {
            println("Updated $new")
        }
    }

    private fun updateImagePainterAndroid(baseFile: File, fileNames: List<Image>) {
        val currentImages = mutableListOf<String>()
        fileNames.forEach {
            val data = """
            Image.${it.name} -> R.drawable.${it.fileName.toLowerCase()}
        """.trimIndent()

            currentImages.add(data)
        }

        val fileData = """
        package ${basePath.replace("/", ".")}.compose.core

        import androidx.compose.runtime.Composable
        import androidx.compose.ui.graphics.painter.Painter
        import androidx.compose.ui.res.painterResource
        import $appName.frontend.resources.R
        import $basePackage.resources.Image

        @Composable
        actual fun imagePainter(image: Image): Painter {
            val imageResource: Int = when (image) {
                ${currentImages.joinToString("\n                ") { it }}
            }

            return painterResource(imageResource)
        }
    """.trimIndent()

        val new = File(
            baseFile,
            "androidMain/kotlin/$basePath/compose/core/Image.android.kt"
        )
        new.ensureParentDirsCreated()
        new.createNewFile()
        new.writeText(fileData)
        println("Updated $new")
    }

    private fun updateImagePainterDesktop(baseFile: File, fileNames: List<Image>) {
        val currentImages = mutableListOf<String>()
        fileNames.forEach {
            val data = """
            Image.${it.name} -> R.drawable.${it.fileName.toLowerCase()}
        """.trimIndent()

            currentImages.add(data)
        }

        val resourcePath = "resourcePath = \"\${image.fileName}.svg\","
        val fileData = """
            package ${basePath.replace("/", ".")}.compose.core

            import androidx.compose.runtime.Composable
            import androidx.compose.ui.ExperimentalComposeUiApi
            import androidx.compose.ui.graphics.painter.Painter
            import androidx.compose.ui.res.FileResourceLoader
            import androidx.compose.ui.res.painterResource
            import $basePackage.resources.Image
            import java.io.File

            @OptIn(ExperimentalComposeUiApi::class)
            @Composable
            actual fun imagePainter(image: Image): Painter {
                // this should work but doesnt for some reason
                // https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Native_distributions_and_local_execution#packaging-resources
                // val file = File(System.getProperty("compose.application.resources.dir"))
                
                val file = File("../resources/build/generated/sources/desktopMain/resources/common")

                return painterResource(
                    $resourcePath
                    loader = FileResourceLoader(file)
                )
            }
        """.trimIndent()

        val new = File(
            baseFile,
            "desktopMain/kotlin/$basePath/compose/core/Image.desktop.kt"
        )
        new.ensureParentDirsCreated()
        new.createNewFile()
        new.writeText(fileData)
        println("Updated $new")
    }

    private fun updateImagePainterCommon(baseFile: File, fileNames: List<Image>) {
        val currentImages = mutableListOf<String>()
        fileNames.forEach {
            val data = """
            Image.${it.name} -> R.drawable.${it.fileName.toLowerCase()}
        """.trimIndent()

            currentImages.add(data)
        }

        val fileData = """
            package ${basePath.replace("/", ".")}.compose.core

            import androidx.compose.runtime.Composable
            import androidx.compose.ui.graphics.painter.Painter
            import $basePackage.resources.Image

            @Composable
            expect fun imagePainter(image: Image): Painter

        """.trimIndent()

        val new = File(
            baseFile,
            "commonMain/kotlin/$basePath/compose/core/Image.kt"
        )
        new.ensureParentDirsCreated()
        new.createNewFile()
        new.writeText(fileData)
        println("Updated $new")
    }

    enum class SvgVariable(val identifier: String) {
        Width("width"),
        Height("height"),
        ViewBox("viewBox"),
        Fill("fill"),
        XMLNS("xmlns");

        companion object {
            fun fromString(data: String): SvgVariable {
                SvgVariable.values().forEach {
                    if (data.startsWith("${it.identifier}="))
                        return it
                }

                throw IllegalArgumentException("$data does not start with a valid svg variable identifier.")
            }
        }

        fun parseData(data: String): String = data.replace("$identifier=", "")
    }

    data class SvgData(
        var width: String = "",
        var height: String = "",
        var viewWidth: String = "",
        var viewHeight: String = "",
        var fill: String = "",
        var paths: List<PathData> = emptyList()
    ) {
        fun toAndroidXml(): String {
            return """
        <vector xmlns:android="http://schemas.android.com/apk/res/android"
            android:width="${width}dp"
            android:height="${height}dp"
            android:viewportWidth="$viewWidth"
            android:viewportHeight="$viewHeight">
            ${paths.map { it.toAndroidXml() }.joinToString("\n  ") { it }}
        </vector>
        """.trimIndent()
        }
    }

    enum class PathVariable(val identifier: String) {
        Data("d"),
        Stroke("stroke"),
        StrokeWidth("stroke-width"),
        StrokeLineCap("stroke-linecap"),
        Fill("fill");

        companion object {
            fun fromString(data: String): PathVariable {
                PathVariable.values().forEach {
                    if (data.startsWith("${it.identifier}="))
                        return it
                }

                throw IllegalArgumentException("$data does not start with a valid path variable identifier.")
            }
        }

        fun parseData(data: String): String = data.replace("$identifier=", "")
    }

    data class PathData(
        var path: String = "",
        var strokeColor: String? = null,
        var strokeWidth: String? = null,
        var strokeLineCap: String? = null,
        var fillColor: String? = null
    ) {
        fun toAndroidXml(): String {
            return """
            <path
                android:pathData="$path"
                ${if (strokeWidth != null) """android:strokeWidth="$strokeWidth"""" else ""}
                ${if (fillColor != null) """android:fillColor="$fillColor"""" else ""}
                ${if (strokeColor != null) """android:strokeColor="$strokeColor"""" else ""}
                ${if (strokeLineCap != null) """android:strokeLineCap="$strokeLineCap"""" else ""}
                />
        """.trimIndent()
        }
    }
}
