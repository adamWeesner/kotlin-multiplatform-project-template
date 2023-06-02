# Wiring up a new UI

After you have [made your fancy new UI](creatingANewUi.md) you need make it possible for the user to
actually see it. That requires that our [navigation](../navigation/readme.md) system needs to know
about it.

## Component

A Component is the interface that represents things the UI can do, things like the Uis state, any
navigational functionality, and calling of apis as examples.

## Wiring up Uis to navigation

### Compose

Continuing on with our example in our [navigation](../navigation/readme.md) readme of creating a
new `Banner` navigational element, and here our example is assuming you made a new `FancyBanner`:

```kotlin
@Composable
fun FancyBanner(
    message: String, // the message shown to the user
    backgroundColor: Color, // the background color of the banner
    image: ByteArray?, // the optional leading image
    action: () -> Unit // the event that happens when the user handles the action (if there is one)
) {
    ...
}
```

To allow for best practices, code re-usability for your Composables, and allowing to wire up your
new UI element to be navigable, we will have to create a component interface and implementation to
pass to the composable, removing all of the variables from it and replacing them with our Component:

```kotlin
@Composable
fun FancyBanner(
    component: FancyBannerComponent
) {
    ...
}
```

Create the interface for the Component:

```kotlin
interface FancyBannerComponent {
    val state: Value<State>

    val onAction: () -> Unit

    fun apiCallOnAction()

    data class State(
        val message: String,
        val backgroundColor: Color,
        val image: ByteArray?,
    )
}
```

We will also need an implementation of the Component to make things actually work:

```kotlin
class DefaultFancyBannerComponent(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext,
    navContext: CoroutineContext,
    override val onAction: () -> Unit,
) : FancyBannerComponent, ComponentContext by componentContext {
    // the base coroutine scope for our component
    private val scope = CoroutineScope(coroutineContext)

    private val _state: MutableValue<FancyBannerComponent.State> =
        MutableValue(FancyBannerComponent.State())
    override val state: Value<FancyBannerComponent.State> = _state

    override fun apiCallInAction() {
        scope.launch {
            ... // your api call logic here

            // when the api call is done and we want to call our nav function
            // this needs to be in the context of our navContext, some UIs require 
            // this specific scope to navigate without crashing
            withContext(navContext) {
                onAction()
            }
        }
    }
}
```

There are some general rules to be followed with these Components:

- Any action the user can take, that ends up in a navigational event, is to be a `val` lambda that
  will get wired up in the `Navigator`
- All values or "state" for the UI should be in a `State` data class inside the Component interface
  for the UI, all state items should be immutable.
- If a function needs a `coroutineScope` to do an action, i.e. a network call, you have two options
    - If your function is being called by the screen and **not** an initialization step it should be
      a non-suspending function using the Components CoroutineScope to launch/async
    - If your function **is** an initalization step use a `LaunchedEffect` or the like in your
      Screen forcing the screen to manage that function calls Coroutine scope, to clean things up
      properly.
    - All functions that are called internal to the Component should be `suspend` functions if they
      need a CoroutineScope, forcing the Component to manage that function calls Coroutine scope, to
      clean things up properly.

Following the rules and having an interface for this state data and actions, allows for easy reuse
of the same UI elements and Components by adding new implementations of the `FancyBannerComponent`.

### Wiring the Component to our navigation system

Now we have our fancy UI element wired up with our Component, now we need to wire it up so that when
the navigation system updates its `bannerSlot` we can show our new UI. For our Multiplatform Compose
based UIs there is a RootContent that is a `BottomSheetScaffold` so we can add our `bannerSlot`
listening there:

```kotlin
@Composable
fun RootContent(
    rootComponent: RootComponent,
    modifier: Modifier = Modifier
) {
    ... // other state listeners
    val bannerSlot by rootComponent.bannerSlot.subscribeAsState()

    // where it fits best
    when (val current = bannerSlot.child?.instance) {
        is Child.FancyBanner -> FancyBanner(current.component)
    }
}
```

### Terminal

if you want your new `bannerSlot` to be usable in
the [terminal](../terminal/src/main/kotlin/com/weesnerDevelopment/lavalamp/terminal/main.kt)
frontend as well. This is similar to the compose stuff, in that the step are the same but the actual
implementation is different.

Wiring up the same `FancyBanner` Component to the terminal, you will first need to make
a [CliktCommand for the FancyBanner](creatingANewUi.md#terminal-frontend) then you can wire it all up:

```kotlin
fun RootContent(
    programArgs: List<String>,
    ... // other params
    component: RootComponent
) = with(component) {
    ... // other stack and slot listeners

    bannerSlot.subscribe {
        val command = when (val current = it.child?.instance) {
            Child.FancyBanner -> FancyBanner(component = current.component)
            null -> null
        }

        // this will run the CliktCommand
        command?.main(programArgs)
    }
}
```