package com.jeanbarrossilva.loadable.placeholder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.loadable.placeholder.test.Loading

/** Default values of a [Placeholder]. **/
object PlaceholderDefaults {
    /** [Color] by which a [Placeholder] is colored by default. **/
    val color
        @Composable get() = MaterialTheme.colorScheme.surfaceVariant

    /** [Shape] by which a [Placeholder] is clipped by default. **/
    val shape
        @Composable get() = MaterialTheme.shapes.medium
}

/**
 * Holds place for loading content.
 *
 * @param loadable [Loadable] that determines whether the [content] is shown.
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param shape [Shape] by which the [Placeholder] is clipped.
 * @param color [Color] by which the [Placeholder] is colored.
 * @param content Content that's shown if the [loadable] is [loaded][Loadable.Loaded].
 **/
@Composable
fun <T> Placeholder(
    loadable: Loadable<T>,
    modifier: Modifier = Modifier,
    shape: Shape = PlaceholderDefaults.shape,
    color: Color = PlaceholderDefaults.color,
    content: @Composable BoxScope.(T) -> Unit
) {
    Placeholder(modifier, isLoading = loadable is Loadable.Loading, shape, color) {
        loadable.ifLoaded {
            content(this)
        }
    }
}

/**
 * Holds place for loading content.
 *
 * @param modifier [Modifier] to be applied to the underlying [Box].
 * @param isLoading Whether the placeholder is visible (instead of the [content]).
 * @param shape [Shape] by which the [Placeholder] is clipped.
 * @param color [Color] by which the [Placeholder] is colored.
 * @param content Content that's shown if [isLoading] is `false`.
 **/
@Composable
fun Placeholder(
    modifier: Modifier = Modifier,
    isLoading: Boolean = true,
    shape: Shape = PlaceholderDefaults.shape,
    color: Color = PlaceholderDefaults.color,
    content: @Composable BoxScope.() -> Unit = { }
) {
    Box(
        modifier
            .placeholder(isLoading, color, shape, PlaceholderHighlight.shimmer())
            .semantics { set(SemanticsProperties.Loading, isLoading) },
        content = content
    )
}

/** Preview of a [Placeholder]. **/
@Composable
@Preview
private fun PlaceholderPreview() {
    MaterialTheme {
        Placeholder(Modifier.size(128.dp))
    }
}
