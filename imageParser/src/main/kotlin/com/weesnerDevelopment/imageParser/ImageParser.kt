package com.weesnerDevelopment.imageParser

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

fun main(args: Array<String>) {
    val images = File("imageParser/src/main/resources/images")
    println("Reading files from: ${images.absolutePath} | ${images.exists()}")

    val fileList = images.listFiles()?.toList() ?: emptyList()
    val scope = CoroutineScope(Dispatchers.Unconfined)

    val fileNames = generateFileNames(fileList)

    scope.launch {
        copySvgs(fileList)
    }

    scope.launch {
        images.listFiles()?.forEach {
            parseSvg(it)
        }
    }

    scope.launch {
        generateClasses(fileNames)
    }

    scope.launch {
        updateImagePainterAndroid(fileNames)
    }
}

private fun copySvgs(files: List<File>) {
    println("starting to copy svgs over...")
    files.forEach {
        println("attempting to copy ${it.name}")
        val new = File("frontend/resources/src/desktopMain/resources/${it.name}")
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
                part.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase() else it.toString()
                }
            }
            fileNames.add(Image(name, it))
        } else {
            val name = it.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }
            fileNames.add(Image(name, it))
        }
    }

    return fileNames
}

private fun generateClasses(fileNames: List<Image>) {
    val classes = """
        package com.weesnerDevelopment.lavalamp.resources
        
        sealed class Image(val fileName: String) {
            ${fileNames.joinToString("\n            ") { it.toString() }}
        }
        
    """.trimIndent()

    val new =
        File("frontend/resources/src/commonMain/kotlin/com/weesnerDevelopment/lavalamp/resources/Image.kt")
    new.createNewFile()
    new.writeText(classes)
    println("Updated $new")
}

private fun parseSvg(data: File) {
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

    val new =
        File("frontend/resources/src/main/res/drawable/${data.nameWithoutExtension.lowercase()}.xml")
    val newFile = new.createNewFile()

    new.writeText(svgData.toAndroidXml())
    if (newFile) {
        println("added $new")
    } else {
        println("Updated $new")
    }
}

private fun updateImagePainterAndroid(fileNames: List<Image>) {
    val new =
        File("frontend/compose/core/src/androidMain/kotlin/com/weesnerDevelopment/compose/core/Image.kt")
    new.createNewFile()

    val currentImages = mutableListOf<String>()
    fileNames.forEach {
        val data = """
            Image.${it.name} -> R.drawable.${it.fileName.lowercase()}
        """.trimIndent()

        currentImages.add(data)
    }

    val fileData = """
        package com.weesnerDevelopment.compose.core

        import androidx.compose.runtime.Composable
        import androidx.compose.ui.graphics.painter.Painter
        import androidx.compose.ui.res.painterResource
        import com.weesnerDevelopment.lavalamp.frontend.resources.R

        @Composable
        actual fun imagePainter(image: Image): Painter {
            val imageResource: Int = when (image) {
                ${currentImages.joinToString("\n                ") { it }}
            }

            return painterResource(imageResource)
        }
    """.trimIndent()

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