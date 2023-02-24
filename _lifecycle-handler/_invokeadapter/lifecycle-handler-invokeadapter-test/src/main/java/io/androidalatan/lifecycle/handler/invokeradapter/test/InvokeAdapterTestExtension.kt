package io.androidalatan.lifecycle.handler.invokeradapter.test

import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class InvokeAdapterTestExtension : BeforeEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        invokeAdapterInitializer()
    }
}
