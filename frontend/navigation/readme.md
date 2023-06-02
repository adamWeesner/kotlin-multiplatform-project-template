## Navigation

In this app we use [Decompose](https://github.com/arkivanov/Decompose) for navigation. This allows
us to create a fully isolated, [customizable](#custom-elements) and simple multiplatform navigational system.

### [RootComponent](src/commonMain/kotlin/com/weesnerDevelopment/navigation/RootComponent.kt)

Is the entrypoint into navigation in this app. Its a very simple interface that contains
the [stacks](#stacknav) and [slots](#slotnav) for each [Child](#child) type that the UIs will
support.

### [StackNav](src/commonMain/kotlin/com/weesnerDevelopment/navigation/StackNav.kt)

An interface that contains its navigator and the stack of children in the StackNav. The navigator is
the internal mechanism that wires all of the navigational stuff together. The stack of children is
a [Value](https://arkivanov.github.io/Decompose/getting-started/quick-start/#using-value-from-decompose)
which can be subscribed to which useful for the UIs to easily listen for changes and react to them.
Generally triggering the actual UI element representing that "screen" to render.

### [SlotNav](src/commonMain/kotlin/com/weesnerDevelopment/navigation/StackNav.kt)

Pretty much the same as StackNav with the exception being the navigator uses a "slot". A slot is
single value for what Child to emit, if ther is one (it is nullable).

### [Config](src/commonMain/kotlin/com/weesnerDevelopment/navigation/Config.kt)

An interface that represents a type-safe way to tell the navigator what [child](#child) to navigate
to. Also, is the container for any parameters you want to pass between different Children. It needs
to have the `@Parcelize` annotation on it. This annotation allows the RootComponent to
serialize/deserialize the any params passed into the Config.

### [Child](src/commonMain/kotlin/com/weesnerDevelopment/navigation/Child.kt)

An interface that represents the thing that navigator navigates to. There are several sub-types of
Child. `Screen`, `Dialog`, `Drawer`, and `BottomSheet` all are general base child types. Each
represents isolated "types" of things the navigator can navigate to. These are isolated for a few
reasons, first being that the UIs generally want to handle these navigational events separately. As
a general example, A Screen in the UI replaces the full viewport, where a Dialog would only show a
small portion of the viewport.

### Custom Elements

This can easily be extended on as well, say you wanted to create a new Child type called `Banner`
that will only update part of the viewport:

Update the `Child` interface with your new type:

```kotlin
sealed interface Child {
    ... // other child elements

    // add an implementation of your new type
    class FancyBanner(val component: FancyBannerComponent) : Banner

    ... // other types

    // add the new child type
    sealed interface Banner : Child
}
```

Add your new `Config` type:

```kotlin
sealed class BannerConfig : Config()
```

Update the `Config` interface using your new type:

```kotlin
sealed class Config : Parcelable {
    ... // other configs

    @Parcelize
    data class SampleForBannerWithParams(
        val param: String
    ) : BannerConfig()

    @Parcelize
    object SampleForBannerWithoutParams : BannerConfig()
}
```

To use the new type you need to do a few things:

In the [RootComponent](#rootcomponent):

```kotlin
interface RootComponent {
    ... // other slot and stack navs

    val bannerSlot: Value<ChildSlot<BannerConfig, Child.Banner>>
}
```

We need to wire everything up the implementation of RootComponent:

```kotlin
internal class DefaultRootComponent(
    ...
    private val bannerFactory: BannerComponentFactory,
) : RootComponent {
    ... // other navs

    private val bannerSlotNav: SlotNav<BannerConfig, Child.Banner> = setupSlotNav(
        key = "BannerSlot",
        platform = platform,
        factory = { config, _ ->
            bannerFactory.create(config, this)
        }
    )

    override val bannerSlot: Value<ChildSlot<BannerConfig, Child.Banner>> = bannerSlotNav.slot

    ...
}
```

### Component Factory

You will need a Factory for your new slot nav for your
Banner [Components](../ui/readme.md#components):

```kotlin
interface BannerComponentFactory {
    fun create(config: BannerConfig, navigator: Navigator): Child.Banner
}

internal class DefaultBannerComponentFactory(
    private val componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val navContext: CoroutineContext,
    ... // other params
) : BannerComponentFactory {
    override fun create(config: BannerConfig, navigator: Navigator): Child.Banner {
        return when (config) {
            is Config.SampleForBannerWithParams -> {
                Child.FancyBanner(
                    DefaultFancyBannerComponent(
                        componentContext = componentContext,
                        coroutineContext = coroutineContext,
                        navContext = navContext,
                        myParam = config.param
                        ... // other params
                )
            }
            is Config.SampleForBannerWithoutParams -> {
                Child.FancyBanner(
                    DefaultFancyBannerComponent(
                        componentContext = componentContext,
                        coroutineContext = coroutineContext,
                        navContext = navContext,
                        ... // other params
                )
            }
        }
    }
}
```

Now you just need to wire up your UIs with your new `bannerSlot` to listen for updates to it.
See [Creating a new UI](../ui/readme.md)


