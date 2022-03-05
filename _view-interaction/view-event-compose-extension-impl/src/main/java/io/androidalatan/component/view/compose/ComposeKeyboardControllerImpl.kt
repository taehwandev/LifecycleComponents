package io.androidalatan.component.view.compose

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.SoftwareKeyboardController
import io.androidalatan.component.view.compose.api.ComposeKeyboardController

class ComposeKeyboardControllerImpl : ComposeKeyboardController {

    @OptIn(ExperimentalComposeUiApi::class)
    private var keyboardController: SoftwareKeyboardController? = null

    @OptIn(ExperimentalComposeUiApi::class)
    override fun showKeyboard() {
        keyboardController?.show()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    override fun hideKeyboard() {
        keyboardController?.hide()
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun setKeyboardController(keyboardController: SoftwareKeyboardController) {
        this.keyboardController = keyboardController
    }
}