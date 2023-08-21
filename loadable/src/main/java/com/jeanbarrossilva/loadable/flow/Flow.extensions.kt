package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.LoadableScope
import com.jeanbarrossilva.loadable.Serializability
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.loadable.map
import java.io.NotSerializableException
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.withIndex

/** Returns a [Flow] containing only [failed][Loadable.Failed] values. **/
fun <T> Flow<Loadable<T>>.filterIsFailed(): Flow<Loadable.Failed<T>> {
    return filterIsInstance()
}

/** Returns a [Flow] containing only [loaded][Loadable.Loaded] values. **/
fun <T> Flow<Loadable<T>>.filterIsLoaded(): Flow<Loadable.Loaded<T>> {
    return filterIsInstance()
}

/**
 * Maps each emitted [loaded][Loadable.Loaded] [Loadable]'s [content][Loadable.Loaded.content] with
 * the given [transform].
 *
 * @param transform Transformation to be done to the [content][Loadable.Loaded.content].
 **/
fun <I, O> Flow<Loadable<I>>.innerMap(transform: suspend (I) -> O): Flow<Loadable<O>> {
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
 * **NOTE**: Emitting a value that cannot be serialized to the resulting [Flow] and performing a
 * terminal operation on it will result while [serializability] is
 * [enforced][Serializability.ENFORCED] in a [NotSerializableException] being thrown.
 *
 * @param serializability Determines whether the values emitted to this [Flow] and sent to the
 * created one should be serializable.
 **/
fun <T> Flow<T>.loadable(serializability: Serializability = Serializability.default):
    Flow<Loadable<T>> {
    return loadableFlow(serializability) {
        collect(::load)
    }
}

/**
 * Terminal operator that turns this into a [Loadable][Loadable] [Flow] and sends its [Loadable]s to
 * the given [loadableScope]. Analogue to calling its...
 *
 * - [LoadableScope.load] when [loading][Loadable.Loading];
 * - [LoadableScope.load] with the [content][Loadable.Loaded.content] when
 * [loaded][Loadable.Loaded];
 * - [LoadableScope.fail] with the [error][Loadable.Failed.error] when [failed][Loadable.Failed].
 *
 * @param loadableScope [LoadableScope] to load [Loadable]s to.
 * @throws NotSerializableException If a value that cannot be serialized is emitted to this [Flow]
 * and, consequently, sent to the [loadableScope].
 **/
@Throws(NotSerializableException::class)
suspend fun <T> Flow<T>.loadTo(loadableScope: LoadableScope<T>) {
    loadable(loadableScope.serializability)
        // Ignores the initial loading stage, since public Loadable-Flow-creator functions'
        // LoadableScope always loads.
        .ignore(1)
        .sendTo(loadableScope)
}

/**
 * Terminal operator that sends [Loadable]s emitted to this [Flow] to the given [loadableScope].
 * Analogue to calling its...
 *
 * - [LoadableScope.load] when [loading][Loadable.Loading];
 * - [LoadableScope.load] with the [content][Loadable.Loaded.content] when
 * [loaded][Loadable.Loaded];
 * - [LoadableScope.fail] with the [error][Loadable.Failed.error] when [failed][Loadable.Failed].
 **/
suspend fun <T> Flow<Loadable<T>>.sendTo(loadableScope: LoadableScope<T>) {
    collect(loadableScope::send)
}

/**
 * Unwraps [Loadable.Loaded] emissions and returns a [Flow] containing only their
 * [content][Loadable.Loaded.content]s.
 **/
fun <T> Flow<Loadable<T>>.unwrap(): Flow<T> {
    return filterIsLoaded().map {
        it.content
    }
}

/**
 * Unwraps [Loadable.Loaded] emissions and returns a [Flow] containing only those that have a
 * non-`null` [content][Loadable.Loaded.content].
 **/
fun <T : Any> Flow<Loadable<T?>>.unwrapContent(): Flow<Loadable<T>> {
    @Suppress("UNCHECKED_CAST")
    return filter {
        it.ifLoaded { this != null } ?: true
    } as Flow<Loadable<T>>
}

/**
 * Creates a [Flow] of [Loadable]s that emits them through [load] with a [LoadableScope] and has an
 * initial [loading][Loadable.Loading] value.
 *
 * @param serializability Determines whether the [loaded][Loadable.Loaded]
 * [content][Loadable.Loaded.content] emitted to to the created [Flow] should be serializable.
 * @param load Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [Flow].
 **/
fun <T> loadableFlow(
    serializability: Serializability = Serializability.default,
    load: suspend LoadableScope<T>.() -> Unit
): Flow<Loadable<T>> {
    return emptyLoadableFlow(serializability) {
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
fun <T> loadableChannelFlow(@BuilderInference block: suspend ProducerScope<Loadable<T>>.() -> Unit):
    Flow<Loadable<T>> {
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
 * @param serializability Determines whether the [loaded][Loadable.Loaded]
 * [content][Loadable.Loaded.content] emitted to to the created [Flow] should be serializable.
 * @param load Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [Flow].
 **/
internal fun <T> emptyLoadableFlow(
    serializability: Serializability = Serializability.default,
    load: suspend LoadableScope<T>.() -> Unit
): Flow<Loadable<T>> {
    return flow<Loadable<T>> {
        FlowCollectorLoadableScope(serializability, this).apply {
            load.invoke(this)
        }
    }
        .catchAsFailed()
}

/**
 * Ignores the first [count] elements.
 *
 * @param count Quantity of initial elements to be ignored. Ideally it'd be greater than zero, since
 * setting it as such simply wouldn't do anything.
 * @throws IllegalArgumentException When [count] is negative.
 **/
private fun <T> Flow<T>.ignore(count: Int): Flow<T> {
    require(count >= 0) { "Count should be positive." }
    return withIndex().filter { it.index >= count }.map { it.value }
}

/** Catches thrown exceptions by emitting a [Loadable.Failed]. **/
private fun <T> Flow<Loadable<T>>.catchAsFailed(): Flow<Loadable<T>> {
    return catch {
        emit(Loadable.Failed(it))
    }
}
