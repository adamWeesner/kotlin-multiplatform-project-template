### Adding an image

Adding an image is a simple process, simply add your `svg` to the [images](../../images) folder, and
sync Gradle.

There is a custom Gradle task to automatically generate the appropriate platform files, in the
correct location. It also adds the image to the `Image` sealed class to be used in compose.

So if you add an svg into the images folder named `myNewImage.svg` it would generate this new object
in the `Image` sealed class:

```kotlin
// the object name is the image name in TitleCase
// the variable `fileName` is the image name minus the file type
//   used to help connect the dots internally
object MyNewImage : Image(fileName = "myNewImage")
```

After the Gradle sync is complete you can freely use your image like this:

```kotlin
@Composable
fun usingMyNewImage() {
    Image(painter = imagePainter(image = Image.MyNewImage))
}
```