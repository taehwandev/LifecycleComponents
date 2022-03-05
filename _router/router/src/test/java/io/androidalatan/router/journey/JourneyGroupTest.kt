package io.androidalatan.router.journey

import android.content.Intent
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import io.androidalatan.router.api.Visitor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JourneyGroupTest {

    private val visitor = mock<Visitor>()
    val intent = mock<Intent>()
    private val journey = JourneyGroup(visitor, intent)

    @Test
    internal fun rawIntent() {
        assertEquals(intent, journey.rawIntent())
    }

    @Test
    fun end() {
        journey.end()
        verify(visitor).end()
    }

    @Test
    fun visit() {
        val argumentCaptor = argumentCaptor<Array<Intent>>()
        journey.visit()
        verify(visitor).visitGroup(argumentCaptor.capture())
        assertEquals(intent, argumentCaptor.firstValue[0])
    }

    @Test
    fun visitForResult() {
        val argumentCaptor = argumentCaptor<Array<Intent>>()
        journey.visitForResult(173)
        verify(visitor).visitGroup(argumentCaptor.capture())
        assertEquals(intent, argumentCaptor.firstValue[0])

    }

    @Test
    fun pendingIntent() {
        Assertions.assertThrows(UnsupportedOperationException::class.java) {
            journey.pendingIntent(1, 2)
        }
    }
}