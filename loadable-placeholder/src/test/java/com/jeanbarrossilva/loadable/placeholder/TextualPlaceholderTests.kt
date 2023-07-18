package com.jeanbarrossilva.loadable.placeholder

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertHeightIsAtLeast
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.platform.app.InstrumentationRegistry
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.placeholder.test.onPlaceholder
import com.jeanbarrossilva.loadable.placeholder.test.tagAsPlaceholder
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class TextualPlaceholderTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun widthIsNotConstrainedIfItIsLoaded() {
        val resources = InstrumentationRegistry.getInstrumentation().context.resources
        val screenWidth = resources.configuration.screenWidthDp.dp
        val screenWidthInPx = resources.displayMetrics.widthPixels
        composeRule.setContent {
            MediumTextualPlaceholder(
                Loadable.Loaded("🙂".repeat(screenWidthInPx)),
                Modifier.tagAsPlaceholder(),
                TextStyle(fontSize = 14.sp)
            ) {
                Text(it)
            }
        }
        composeRule.onPlaceholder().assertWidthIsEqualTo(screenWidth)
    }

    @Test
    fun heightIsNotConstrainedIfItIsLoaded() {
        val screenHeightInPx = InstrumentationRegistry
            .getInstrumentation()
            .context
            .resources
            .displayMetrics
            .heightPixels
        composeRule.setContent {
            MediumTextualPlaceholder(
                Loadable.Loaded("🙃".repeat(screenHeightInPx)),
                Modifier.tagAsPlaceholder(),
                TextStyle(fontSize = 14.sp)
            ) {
                Text(it)
            }
        }
        composeRule.onPlaceholder().assertHeightIsAtLeast(24.dp)
    }

    @Test
    fun passedTextStyleIsSetAsTheLocalOne() {
        composeRule.setContent {
            MediumTextualPlaceholder(
                Loadable.Loaded("🤌🏽"),
                style = MaterialTheme.typography.headlineLarge
            ) {
                val expected = MaterialTheme.typography.headlineLarge
                val actual = LocalTextStyle.current

                DisposableEffect(Unit) {
                    assertEquals(expected, actual)
                    onDispose { }
                }
            }
        }
    }
}
