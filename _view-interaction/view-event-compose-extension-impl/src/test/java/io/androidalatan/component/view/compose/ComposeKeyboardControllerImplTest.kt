package io.androidalatan.component.view.compose

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ComposeKeyboardControllerImplTest {

    private val controller = ComposeKeyboardControllerImpl()

    @OptIn(ExperimentalComposeUiApi::class)
    private val softwareKeyboardController: SoftwareKeyboardController = mock()

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun showKeyboard() {
        controller.setKeyboardController(softwareKeyboardController)

        controller.showKeyboard()
        verify(softwareKeyboardController).show()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Test
    fun hideKeyboard() {
        controller.setKeyboardController(softwareKeyboardController)

        controller.hideKeyboard()
        verify(softwareKeyboardController).hide()
    }
}