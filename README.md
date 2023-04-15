# Loadable

<img src="https://user-images.githubusercontent.com/38408390/232172469-f9a42156-76dd-45e4-aafa-334314273c89.png">

## Stages

The main interface in this library, [`Loadable`](https://github.com/jeanbarrossilva/loadable/blob/main/loadable/src/main/java/com/jeanbarrossilva/loadable/Loadable.kt), represents three different stages of asynchronously-loaded content.

### `Loading`

Stage in which the content is being loaded and, therefore, is temporarily unavailable.

### `Loaded`

Stage in which the content has been successfully loaded. Holds the content itself.

### `Failed`

Stage in which the content has failed to load and threw an error. Holds a [`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/).

## Usage

Loadable relies heavily on Kotlin's [coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [`Flow`](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow)s, offering functions and extensions that can, in turn, convert any (literally, [`Any`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-any/)) structure or, specifically, [`Flow`](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/)s into loadable data.

Suppose, for example, that we have a mobile app in which want to display a sequence of numbers fetched from the server, and also inform whether it's loading or if it has failed:

```kotlin
coroutineScope.launch {
    loadableFlow {
        // `getNumbersFlow` is a suspending network call that returns Flow<Int>.
        getNumbersFlow().collect {
            // `load` is one of the methods of the LoadableScope<T> provided by `loadableFlow`,
            // which simply emits the collected value from `getNumbersFlow` to the
            // `loadableFlow` itself.
            load(it)
        }
    }
        .collect {
            when (it) {
                is Loadable.Loading -> display("Loading...")
                is Loadable.Loaded -> display(it.value)
                is Loadable.Failed -> display(it.error.message)
            }
        }
}
```
