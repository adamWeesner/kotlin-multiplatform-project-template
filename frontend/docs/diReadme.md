## Dependency Injection

We use [Kodein](https://kosi-libs.org/kodein) for our dependency injection.

### creating things using DI

If we are continuing with our example of having a `FancyBanner` we created:

- [FancyBannerComponent](../ui/wiringUpANewUi.md#component)
- [Wiring it up with Navigation](../navigation/readme.md)
- [The FancyBanner UI](../ui/creatingANewUi.md)

Now we want to use dependency injection to connect the missing dots.

### Navigation

First we can create
the [BannerComponentFactory](../navigation/readme.md#component-factory) using Kodein:

```kotlin
private val factoryModule by DI.Module {
    ... // other bindings

    bindSingleton<BannerComponentFactory> {
        DefaultBannerComponentFactory(
            componentContext = instance(),
            coroutineContext = instance("general"),
            navContext = instance("navigation"),
            ... // other params
        )
    }
}
```

### New DI Module

To create a new `DI.Module` add it to its respective Gradle module, as an example a new api module
to make the `FancyBannerComponent#apiCallOnAction` function:

```kotlin
val apiModule by DI.Module {
    // add any apis you want to wire up
}
```

From here we need to determine where to add this new module in the dependency graph. Generally this
would live in
the [top most DI](../di/src/commonMain/kotlin/com/weesnerDevelopment/lavalamp/di/Setup.kt):

```kotlin
fun setupDI(
    ...
): DI = DI {
    ... // other imports
    
    import(apiModule)
}
```
