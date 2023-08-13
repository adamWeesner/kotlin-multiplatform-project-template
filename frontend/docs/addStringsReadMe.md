### Adding a String

There is an interface called [AllStrings](../resources/src/commonMain/kotlin/com/weesnerDevelopment/lavalamp/frontend/resources/strings/AllStrings.kt) 
that represents all reusable string resource for the project. Any language supported must have a 
corresponding class/object in the format of `CountryCode(ISO 3166-1 alpha-3)-LanguageCode(ISO 639-2)`
inside the [strings/variants](../resources/src/commonMain/kotlin/com/weesnerDevelopment/lavalamp/frontend/resources/strings/variant) folder
see [United States English](../resources/src/commonMain/kotlin/com/weesnerDevelopment/lavalamp/frontend/resources/strings/variant/Eng-USA.kt)
as an example.

To add a string simply add to the `AllStrings` interface:

for a constant string:
```kotlin
val MyNewString: String
```

for a string that has modifiable parts:
NOTE: the `variable1: String` can be represented by ANY valid Kotlin variable type. The generated 
`Strings.kt` file will automatically pull any imports in the `AllStrings.kt` file.

```kotlin
fun MyNewVariableString(variable1: String): String
```

From there for the project to compile you will need to add the implementation of the above string to
each `variant` implementation. The generated Strings will automatically wire up the corresponding 
`{Country}-{Language}.kt` appropriately.

Sync Gradle and you should be able to use your new string like this:

```kotlin
println("my new string is: ${Strings.MyNewString}")
println("my new string with variables is: ${Strings.MyNewVariableString("customVariable")}")
```