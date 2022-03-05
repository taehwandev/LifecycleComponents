package io.androidalatan.router.journey

import android.content.Intent
import io.androidalatan.router.api.Visitor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ServiceJourneyTest {

    private val visitor = mock<Visitor>()
    private val intent = mock<Intent>()
    private val journey = ServiceJourney(visitor, intent)

    @Test
    fun rawIntent() {
        Assertions.assertEquals(intent, journey.rawIntent())
    }

    @Test
    fun pendingIntent() {
        val reqCode = 123
        val flags = 1245
        journey.pendingIntent(reqCode, flags)
        verify(visitor).getPendingIntent(reqCode, flags, intent)
    }

    @Test
    fun end() {
        journey.end()
        verify(visitor).end()
    }

    @Test
    fun visit() {
        journey.visit()
        verify(visitor).visitService(intent)
    }

    @Test
    fun visitForResult() {
        val reqCode = 3712
        journey.visitForResult(reqCode)
        verify(visitor).visitService(intent)
    }
}