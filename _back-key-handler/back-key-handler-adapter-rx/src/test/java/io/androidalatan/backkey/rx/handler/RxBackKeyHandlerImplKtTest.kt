package io.androidalatan.backkey.rx.handler

import io.androidalatan.backkey.handler.assertion.MockBackKeyHandlerStream
import org.junit.jupiter.api.Test

internal class RxBackKeyHandlerImplKtTest {

    @Test
    fun onBackPressedAsObs() {
        val backKeyHandlerStream = MockBackKeyHandlerStream()
        val testObserver = backKeyHandlerStream.onBackPressedAsObs()
            .test()
            .assertNoErrors()
            .assertNoErrors()
            .assertNotComplete()

        backKeyHandlerStream.executeCallbacks()
        testObserver.assertValueCount(1)
            .assertValue(0)
            .assertNoErrors()
            .assertNotComplete()

        backKeyHandlerStream.executeCallbacks()
        testObserver.assertValueCount(2)
            .assertValueAt(1, 1)
            .assertNoErrors()
            .assertNotComplete()
            .dispose()

    }
}