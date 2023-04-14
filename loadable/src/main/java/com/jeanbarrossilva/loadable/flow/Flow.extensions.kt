package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.LoadableScope
import com.jeanbarrossilva.loadable.map
import java.io.Serializable
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.emitAll
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
    return loadable(coroutineScope) {
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
    return loadable<T> {
        collect(::load)
    }
}

/**
 * Unwraps [Loadable.Loaded] emissions and returns a [Flow] containing only their
 * [Loadable.Loaded.value]s.
 **/
fun <T : Serializable?> Flow<Loadable<T>>.unwrap(): Flow<T> {
    return filterIsLoaded().map {
        it.value
    }
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
fun <T : Serializable?> loadable(
    coroutineScope: CoroutineScope,
    load: suspend LoadableScope<T>.() -> Unit
): StateFlow<Loadable<T>> {
    return MutableStateFlow<Loadable<T>>(Loadable.Loading())
        .apply {
            coroutineScope.launch {
                emitAll(emptyLoadable(load))
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
fun <T : Serializable?> loadable(load: suspend LoadableScope<T>.() -> Unit):
    Flow<Loadable<T>> {
    return emptyLoadable {
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

/** Catches thrown exceptions by emitting a [Loadable.Failed]. **/
private fun <T : Serializable?> Flow<Loadable<T>>.catchAsFailed(): Flow<Loadable<T>> {
    return catch {
        emit(Loadable.Failed(it))
    }
}

/**
 * Creates a [Flow] of [Loadable]s that emits them through [load] with a [LoadableScope]. Doesn't
 * have any initial value (hence its emptiness).
 *
 * @param load Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [Flow].
 **/
private fun <T : Serializable?> emptyLoadable(load: suspend LoadableScope<T>.() -> Unit):
    Flow<Loadable<T>> {
    return flow<Loadable<T>> {
        FlowCollectorLoadableScope(this).apply {
            load.invoke(this)
        }
    }
        .catchAsFailed()
}
