package io.androidalatan.backkey.handler

import io.androidalatan.backkey.handler.api.BackKeyHandlerStream
import io.androidalatan.backkey.handler.api.BackKeyObserver
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BackKeyHandlerStreamImplTest {

    private val backKeyHandler = BackKeyHandlerStreamImpl()

    @Test
    fun setBackKeyObserver() {
        val backKeyObserver = object : BackKeyObserver {
            override fun initIfNeed(handlerCallbacks: () -> List<BackKeyHandlerStream.Callback>) {
                TODO("Not yet implemented")
            }

            override fun deinit() {
                TODO("Not yet implemented")
            }
        }
        backKeyHandler.setBackKeyObserver(backKeyObserver)
        assertEquals(backKeyHandler.backKeyObserver, backKeyObserver)
    }

    @Test
    fun registerCallback() {
        val callback = BackKeyHandlerStream.Callback { }
        backKeyHandler.registerCallback(callback)
        assertTrue(backKeyHandler.callbacks.contains(callback))

        backKeyHandler.unregisterCallback(callback)
        assertFalse(backKeyHandler.callbacks.contains(callback))
    }
}