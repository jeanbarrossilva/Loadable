package com.jeanbarrossilva.loadable.placeholder

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.placeholder.test.assertIsLoading
import com.jeanbarrossilva.loadable.placeholder.test.assertIsNotLoading
import com.jeanbarrossilva.loadable.placeholder.test.onPlaceholder
import com.jeanbarrossilva.loadable.placeholder.test.tagAsPlaceholder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class PlaceholderTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun isLoadingWhenIsLoadingIsSetToTrue() {
        composeRule.setContent { Placeholder(Modifier.tagAsPlaceholder()) }
        composeRule.onPlaceholder().assertIsLoading()
    }

    @Test
    fun isLoadingWhenLoadableIsLoading() {
        composeRule.setContent {
            Placeholder(Loadable.Loading<Int>(), Modifier.tagAsPlaceholder()) {
            }
        }
        composeRule.onPlaceholder().assertIsLoading()
    }

    @Test
    fun isNotLoadingWhenLoadableIsLoaded() {
        composeRule.setContent {
            Placeholder(Loadable.Loaded(0), Modifier.tagAsPlaceholder()) {
            }
        }
        composeRule.onPlaceholder().assertIsNotLoading()
    }

    @Test
    fun isNotLoadingWhenLoadableIsFailed() {
        composeRule.setContent {
            Placeholder(Loadable.Failed(Exception()), Modifier.tagAsPlaceholder()) {
            }
        }
        composeRule.onPlaceholder().assertIsNotLoading()
    }
}
