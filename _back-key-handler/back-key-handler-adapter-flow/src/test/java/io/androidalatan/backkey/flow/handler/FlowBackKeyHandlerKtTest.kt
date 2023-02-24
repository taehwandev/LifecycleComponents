package io.androidalatan.backkey.flow.handler

import app.cash.turbine.test
import io.androidalatan.backkey.handler.assertion.MockBackKeyHandlerStream
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class FlowBackKeyHandlerKtTest {

    @Test
    fun onBackPressedAsFlow() = runTest {
        val backKeyHandlerStream = MockBackKeyHandlerStream()
        backKeyHandlerStream
            .onBackPressedAsFlow()
            .test {
                expectNoEvents()

                backKeyHandlerStream.executeCallbacks()
                Assertions.assertEquals(0, awaitItem())

                backKeyHandlerStream.executeCallbacks()
                Assertions.assertEquals(1, awaitItem())

            }

    }
}