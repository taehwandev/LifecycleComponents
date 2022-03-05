package io.androidalatan.backkey.flow.handler

import io.androidalatan.backkey.handler.assertion.MockBackKeyHandlerStream
import io.androidalatan.coroutine.test.turbine
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FlowBackKeyHandlerKtTest {

    @Test
    fun onBackPressedAsFlow() {
        val backKeyHandlerStream = MockBackKeyHandlerStream()
        backKeyHandlerStream
            .onBackPressedAsFlow()
            .turbine { flowTurbine ->
                flowTurbine.expectNoEvents()

                backKeyHandlerStream.executeCallbacks()
                Assertions.assertEquals(0, flowTurbine.awaitItem())

                backKeyHandlerStream.executeCallbacks()
                Assertions.assertEquals(1, flowTurbine.awaitItem())

            }

    }
}