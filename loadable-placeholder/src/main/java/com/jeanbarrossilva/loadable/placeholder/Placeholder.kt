package com.jeanbarrossilva.loadable.placeholder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.ifLoaded
import com.jeanbarrossilva.loadable.placeholder.test.Loading
import java.io.Serializable

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
fun <T : Serializable?> Placeholder(
    loadable: Loadable<T>,
    modifier: Modifier = Modifier,
    shape: Shape = PlaceholderDefaults.shape,
    color: Color = PlaceholderDefaults.color,
    content: @Composable T.() -> Unit
) {
    Placeholder(modifier, isVisible = loadable is Loadable.Loading, shape, color) {
        loadable.ifLoaded {
            content()
        }
    }
}

/**
 * Holds place for loading content.
 *
 * @param modifier [Modifier] to be applied to the underlying [Box].
 * @param isVisible Whether the placeholder is visible (instead of the [content]).
 * @param shape [Shape] by which the [Placeholder] is clipped.
 * @param color [Color] by which the [Placeholder] is colored.
 * @param content Content that's shown if [isVisible] is `false`.
 **/
@Composable
fun Placeholder(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    shape: Shape = PlaceholderDefaults.shape,
    color: Color = PlaceholderDefaults.color,
    content: @Composable () -> Unit = { }
) {
    Box(
        modifier
            .placeholder(isVisible, color, shape, PlaceholderHighlight.shimmer())
            .semantics { set(SemanticsProperties.Loading, isVisible) }
    ) {
        content()
    }
}

/**
 * Holds place for large, loading [Text].
 *
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 **/
@Composable
fun LargeTextualPlaceholder(
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = PlaceholderDefaults.color
) {
    LargeTextualPlaceholder(isVisible = true, style, color, modifier)
}

/**
 * Holds place for large, loading [Text].
 *
 * @param loadable [Loadable] that determines whether the [content] is shown.
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 * @param content [Text] that's shown if the [loadable] is [loaded][Loadable.Loaded].
 **/
@Composable
fun LargeTextualPlaceholder(
    loadable: Loadable<String>,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = PlaceholderDefaults.color,
    content: @Composable String.() -> Unit
) {
    LargeTextualPlaceholder(isVisible = loadable is Loadable.Loading, style, color, modifier) {
        loadable.ifLoaded {
            content()
        }
    }
}

/**
 * Holds place for medium, loading [Text].
 *
 * @param loadable [Loadable] that determines whether the [content] is shown.
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 * @param content [Text] that's shown if the [loadable] is [loaded][Loadable.Loaded].
 **/
@Composable
fun MediumTextualPlaceholder(
    loadable: Loadable<String>,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = PlaceholderDefaults.color,
    content: @Composable String.() -> Unit
) {
    MediumTextualPlaceholder(isVisible = loadable is Loadable.Loading, style, color, modifier) {
        loadable.ifLoaded {
            content()
        }
    }
}

/**
 * Holds place for medium, loading [Text].
 *
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 **/
@Composable
fun MediumTextualPlaceholder(
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = PlaceholderDefaults.color
) {
    MediumTextualPlaceholder(isVisible = true, style, color, modifier)
}

/**
 * Holds place for small, loading [Text].
 *
 * @param loadable [Loadable] that determines whether the [content] is shown.
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 * @param content [Text] that's shown if the [loadable] is [loaded][Loadable.Loaded].
 **/
@Composable
fun SmallTextualPlaceholder(
    loadable: Loadable<String>,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = PlaceholderDefaults.color,
    content: @Composable String.() -> Unit
) {
    SmallTextualPlaceholder(isVisible = loadable is Loadable.Loading, style, color, modifier) {
        loadable.ifLoaded {
            content()
        }
    }
}

/**
 * Holds place for small, loading [Text].
 *
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 **/
@Composable
fun SmallTextualPlaceholder(
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = PlaceholderDefaults.color
) {
    SmallTextualPlaceholder(isVisible = true, style, color, modifier)
}

/**
 * Holds place for large, loading [Text].
 *
 * @param isVisible Whether the placeholder is visible (instead of the [content]).
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param content [Text] that's shown if the [loadable] is [loaded][Loadable.Loaded].
 **/
@Composable
private fun LargeTextualPlaceholder(
    isVisible: Boolean,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = { }
) {
    TextualPlaceholder(isVisible, fraction = 1f, style, color, modifier, content)
}

/**
 * Holds place for medium, loading [Text].
 *
 * @param isVisible Whether the placeholder is visible (instead of the [content]).
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param content [Text] that's shown if the [loadable] is [loaded][Loadable.Loaded].
 **/
@Composable
private fun MediumTextualPlaceholder(
    isVisible: Boolean,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = { }
) {
    TextualPlaceholder(isVisible, fraction = .5f, style, color, modifier, content)
}

/**
 * Holds place for small, loading [Text].
 *
 * @param isVisible Whether the placeholder is visible (instead of the [content]).
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param content [Text] that's shown if the [loadable] is [loaded][Loadable.Loaded].
 **/
@Composable
private fun SmallTextualPlaceholder(
    isVisible: Boolean,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = { }
) {
    TextualPlaceholder(isVisible, fraction = .2f, style, color, modifier, content)
}

/**
 * Holds place for loading [Text].
 *
 * @param isVisible Whether the placeholder is visible (instead of the [content]).
 * @param fraction Available-width-based fraction.
 * @param style [TextStyle] for determining the height.
 * @param color [Color] by which the [TextualPlaceholder] is colored.
 * @param modifier [Modifier] to be applied to the underlying [Placeholder].
 * @param content [Text] that's shown if [isVisible] is `false`.
 **/
@Composable
private fun TextualPlaceholder(
    isVisible: Boolean,
    fraction: Float,
    style: TextStyle,
    color: Color,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = { }
) {
    val height = with(LocalDensity.current) {
        style.fontSize.toDp()
    }

    Placeholder(
        modifier
            .`if`(isVisible) { requiredHeight(height) }
            .`if`(isVisible) { fillMaxWidth(fraction) },
        isVisible,
        shapeFor(style),
        color,
        content
    )
}

/**
 * [Shape] that's equivalent to the [textStyle].
 *
 * @param textStyle [TextStyle] for which the [Shape] is.
 **/
@Composable
private fun shapeFor(textStyle: TextStyle): Shape {
    return when (textStyle) {
        MaterialTheme.typography.displayLarge,
        MaterialTheme.typography.displayMedium,
        MaterialTheme.typography.displaySmall,
        MaterialTheme.typography.headlineLarge,
        MaterialTheme.typography.headlineMedium,
        MaterialTheme.typography.headlineSmall -> MaterialTheme.shapes.large
        MaterialTheme.typography.labelLarge,
        MaterialTheme.typography.labelMedium,
        MaterialTheme.typography.labelSmall -> MaterialTheme.shapes.small
        else -> PlaceholderDefaults.shape
    }
}

/** Preview of a [Placeholder]. **/
@Composable
@Preview
private fun PlaceholderPreview() {
    MaterialTheme {
        Placeholder(Modifier.size(128.dp))
    }
}

/** Preview of a [LargeTextualPlaceholder]. **/
@Composable
@Preview
private fun LargeTextualPlaceholderPreview() {
    MaterialTheme {
        LargeTextualPlaceholder()
    }
}

/** Preview of a [MediumTextualPlaceholder]. **/
@Composable
@Preview
private fun MediumTextualPlaceholderPreview() {
    MaterialTheme {
        MediumTextualPlaceholder()
    }
}

/** Preview of a [SmallTextualPlaceholder]. **/
@Composable
@Preview
private fun SmallTextualPlaceholderPreview() {
    MaterialTheme {
        SmallTextualPlaceholder()
    }
}
