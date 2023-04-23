package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.LoadableScope
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.loadable.map
import java.io.Serializable
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

/** Returns a [Flow] containing only [failed][Loadable.Failed] values. **/
fun <T : Serializable?> Flow<Loadable<T>>.filterIsFailed(): Flow<Loadable.Failed<T>> {
    return filterIsInstance()
}

/** Returns a [Flow] containing only [loaded][Loadable.Loaded] values. **/
fun <T : Serializable?> Flow<Loadable<T>>.filterIsLoaded(): Flow<Loadable.Loaded<T>> {
    return filterIsInstance()
}

/**
 * Maps each emitted [Loadable] with the given [transform].
 *
 * @param transform Transformation to be done to the [Loadable]s.
 **/
fun <I : Serializable?, O : Serializable?> Flow<Loadable<I>>.innerMap(transform: suspend (I) -> O):
    Flow<Loadable<O>> {
    return map { loadable: Loadable<I> ->
        loadable.map {
            transform(it)
        }
    }
}

/**
 * Maps each emission made to this [Flow] to a [Loadable].
 *
 * Emits, initially, [Loadable.Loading], [Loadable.Loaded] for each value and [Loadable.Failed] for
 * thrown [Throwable]s.
 *
 * @param coroutineScope [CoroutineScope] in which the resulting [StateFlow] will be started and its
 * value will be shared.
 **/
fun <T : Serializable?> Flow<T>.loadable(coroutineScope: CoroutineScope): StateFlow<Loadable<T>> {
    return loadableFlow(coroutineScope) {
        collect(::load)
    }
}

/**
 * Maps each emission made to this [Flow] to a [Loadable].
 *
 * Emits, initially, [Loadable.Loading], [Loadable.Loaded] for each value and [Loadable.Failed] for
 * thrown [Throwable]s.
 **/
fun <T : Serializable?> Flow<T>.loadable(): Flow<Loadable<T>> {
    return loadableFlow {
        collect(::load)
    }
}

/**
 * Terminal operator that sends [Loadable]s emitted to this [Flow] into the given [loadableScope],
 * calling its...
 *
 * - [LoadableScope.load] when [loading][Loadable.Loading];
 * - [LoadableScope.load] with the [content][Loadable.Loaded.content] when
 * [loaded][Loadable.Loaded];
 * - [LoadableScope.fail] with the [error][Loadable.Failed.error] when [failed][Loadable.Failed].
 **/
suspend fun <T : Serializable?> Flow<Loadable<T>>.loadInto(loadableScope: LoadableScope<T>) {
    collect {
        when (it) {
            is Loadable.Loading -> loadableScope.load()
            is Loadable.Loaded -> loadableScope.load(it.content)
            is Loadable.Failed -> loadableScope.fail(it.error)
        }
    }
}

/**
 * Unwraps [Loadable.Loaded] emissions and returns a [Flow] containing only their
 * [content][Loadable.Loaded.content]s.
 **/
fun <T : Serializable?> Flow<Loadable<T>>.unwrap(): Flow<T> {
    return filterIsLoaded().map {
        it.content
    }
}

/**
 * Unwraps [Loadable.Loaded] emissions and returns a [Flow] containing only those that have a
 * non-`null` [content][Loadable.Loaded.content].
 **/
fun <T : Serializable> Flow<Loadable<T?>>.unwrapContent(): Flow<Loadable<T>> {
    @Suppress("UNCHECKED_CAST")
    return filter {
        it.ifLoaded { this != null } ?: true
    } as Flow<Loadable<T>>
}

/**
 * Creates a [StateFlow] of [Loadable]s that's started and shared in the [coroutineScope] and emits
 * them through [load] with a [LoadableScope]. Its initial [value][StateFlow.value] is
 * [loading][Loadable.Loading].
 *
 * @param coroutineScope [CoroutineScope] in which the resulting [StateFlow] will be started and its
 * value will be shared.
 * @param load Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [StateFlow].
 **/
fun <T : Serializable?> loadableFlow(
    coroutineScope: CoroutineScope,
    load: suspend LoadableScope<T>.() -> Unit
): StateFlow<Loadable<T>> {
    return loadableFlow<T>()
        .apply {
            coroutineScope.launch {
                emitAll(emptyLoadableFlow(load))
            }
        }
        .asStateFlow()
}

/**
 * Creates a [Flow] of [Loadable]s that emits them through [load] with a [LoadableScope] and has an
 * initial [loading][Loadable.Loading] value.
 *
 * @param load Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [Flow].
 **/
fun <T : Serializable?> loadableFlow(load: suspend LoadableScope<T>.() -> Unit):
    Flow<Loadable<T>> {
    return emptyLoadableFlow {
        load()
        load.invoke(this)
    }
}

/**
 * Creates a [Flow] through [channelFlow] with a [Loadable.Loading] as its initial value.
 *
 * @param block Production to be run whenever a terminal operator is applied to the resulting
 * [Flow].
 **/
@OptIn(ExperimentalTypeInference::class)
fun <T : Serializable?> loadableChannelFlow(
    @BuilderInference block: suspend ProducerScope<Loadable<T>>.() -> Unit
): Flow<Loadable<T>> {
    return channelFlow {
        send(Loadable.Loading())
        block()
    }
        .catchAsFailed()
}

/**
 * Creates a [Flow] of [Loadable]s that emits them through [load] with a [LoadableScope]. Doesn't
 * have any initial value (hence its emptiness).
 *
 * @param load Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [Flow].
 **/
internal fun <T : Serializable?> emptyLoadableFlow(load: suspend LoadableScope<T>.() -> Unit):
    Flow<Loadable<T>> {
    return flow<Loadable<T>> {
        FlowCollectorLoadableScope(this).apply {
            load.invoke(this)
        }
    }
        .catchAsFailed()
}

/** Catches thrown exceptions by emitting a [Loadable.Failed]. **/
private fun <T : Serializable?> Flow<Loadable<T>>.catchAsFailed(): Flow<Loadable<T>> {
    return catch {
        emit(Loadable.Failed(it))
    }
}
