package com.jeanbarrossilva.loadable.placeholder

import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertWidthIsEqualTo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.test.platform.app.InstrumentationRegistry
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.placeholder.test.onPlaceholder
import com.jeanbarrossilva.loadable.placeholder.test.tagAsPlaceholder
import org.junit.Rule
import org.junit.Test

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
                Text(this)
            }
        }
        composeRule.onPlaceholder().assertWidthIsEqualTo(screenWidth)
    }
}
