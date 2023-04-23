# Loadable

<img src="https://user-images.githubusercontent.com/38408390/232172469-f9a42156-76dd-45e4-aafa-334314273c89.png">

## Download

Loadable is available at [Maven Central](https://central.sonatype.com), so make sure you add it to your project-level `build.gradle`:

```kotlin
allprojects {
    repositories {
        mavenCentral()
    }
}
```

Then, import the library into your module (replacing "\<version>" by the latest version that's available):

```kotlin
dependencies {
    implementation("com.jeanbarrossilva.loadable:loadable:<version>")
}
```


## Stages

The main interface in this library, [`Loadable`](https://github.com/jeanbarrossilva/loadable/blob/main/loadable/src/main/java/com/jeanbarrossilva/loadable/Loadable.kt), represents three different stages of asynchronously-loaded content.

### `Loading`

Stage in which the content is being loaded and, therefore, is temporarily unavailable.

### `Loaded`

Stage in which the content has been successfully loaded. Holds the content itself.

### `Failed`

Stage in which the content has failed to load and threw an error. Holds a
[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/).

## Usage

Loadable relies heavily on Kotlin's
[coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and
[`Flow`](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow)s,
offering functions and extensions that can, in turn, convert any (literally,
[`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/)) structure or, specifically,
[`Flow`](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)s
into loadable data.

Suppose, for example, that we have an app in which we want to display a sequence of numbers fetched
from the server, and also inform whether it's loading or if it has failed:

```kotlin
CoroutineScope(Dispatchers.IO).launch {
    loadableFlow { // this: LoadableScope<Int>
        // `getNumbersFlow` is a suspending network call that returns Flow<Int>.
        getNumbersFlow()
        // `loadTo` is a terminal operator that turns `Flow<Int>` into a `Flow<Loadable<Int>>` and
        // emits all of its Loadables to the outer `loadableFlow`.
            .loadTo(this)
    }
        .collect {
            withContext(Dispatchers.Main) {
                when (it) {
                    is Loadable.Loading -> display("Loading...")
                    is Loadable.Loaded -> display(it.content)
                    is Loadable.Failed -> display(it.error.message)
                }
            }
        }
}
```

In this scenario, `loadableFlow` was used purely because it has a lambda through which we can
collect other [`Flow`](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow)s
suspendingly and `getNumbersFlow` is a suspending function. If that wasn't the case and we wanted to
just transform a `Flow<T>` into a `Flow<Loadable<T>>`, it could be accomplished by one of the
following:

```kotlin
val coroutineScope = CoroutineScope(Dispatchers.IO)
val numbersFlow = flowOf(1, 2, 3, 4)

// Returns Flow<Loadable<Int>>.
numbersFlow.loadable()

// Returns StateFlow<Loadable<Int>>, started and with its value shared in the given coroutine scope.
numbersFlow.loadable(coroutineScope)
```
