### Adding an image

To add an image simply add your svg to [resources/images](src/main/resources/images) then run the
main function [here](src/main/kotlin/com/weesnerDevelopment/imageParser/ImageParser.kt). Then you can
use your image :)
```kotlin
@Composable
fun usingMyImage() {
    imagePainter(Image.MyNewImage)
}
```
