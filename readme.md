### lavalamp

#### Where to add new reusable Composables

If you're adding a new composable that is meant to be a reusable "core" thing add it to 
[compose/core](frontend/compose/core/src/commonMain/kotlin/com/weesnerDevelopment/compose/core) 

If you're adding a new "screen" add it to its respective Gradle module in [ui](frontend/ui)

Each each screen/dialog/bottomsheet/drawer has its own "Component" in the things respective Gradle 
module noted above.

After adding a screen/dialog/etc you need to wire it up in a few places:

 - add a new element to the [Child](frontend/navigation/src/commonMain/kotlin/com/weesnerDevelopment/navigation/Child.kt)

```kotlin
class MyNewUi(val component: MyNewUisComponent) : Child
```

- add a new element to the [Config](frontend/navigation/src/commonMain/kotlin/com/weesnerDevelopment/navigation/Config.kt)

```kotlin
// needs to be parcelable so that state can be saved internally
@Parcelize
data class MyNewUi(
    // any args...
) : Config()
```

 - in the [RootComponent](frontend/navigation/src/commonMain/kotlin/com/weesnerDevelopment/navigation/RootComponent.kt) 
inside the `createChild` function add:

```kotlin
Child.MyNewUi(
    MyNewUisComponent(
        componentContext = componentContext,
        coroutineContext = coroutineContext,
        navContext = navContext,
        onActionThatNavigates = {
            // todo need to show a dialog or something here
            navigation.pop()
        },
    )
)
```

- wire up the new Config to the [RootContent](frontend/navigation/src/commonMain/kotlin/com/weesnerDevelopment/navigation/RootContent.kt) 
so that the Compose Uis know what to do with the navigation classes

```kotlin
// this is the import for the ui
import com.weesnerDevelopment.lavalamp.ui.myNewUi.MyNewUi

// this goes with the other Child selections
is Child.MyNewUi -> MyNewUi(current.component)
```

- the last 2 things need to happen for the [terminal](frontend/terminal/src/main/kotlin/com/weesnerDevelopment/lavalamp/terminal/main.kt)