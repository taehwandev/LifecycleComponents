package io.androidalatan.router.journey

import android.app.PendingIntent
import android.content.Intent
import io.androidalatan.router.api.Visitor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever

class ActivityJourneyTest {
    private val intent = Mockito.mock(Intent::class.java)
    private val visitor = Mockito.mock(Visitor::class.java)
    private val journey = ActivityJourney(visitor, intent)

    @Test
    fun rawIntent() {
        Assertions.assertEquals(intent, journey.rawIntent())
    }

    @Test
    fun pendingIntent() {
        val pendingIntent = Mockito.mock(PendingIntent::class.java)
        whenever(visitor.getPendingIntent(any(), any(), eq(intent))).thenReturn(pendingIntent)

        val reqCode = 1
        val flags = 2
        Assertions.assertEquals(pendingIntent, journey.pendingIntent(reqCode, flags))
        Mockito.verify(visitor)
            .getPendingIntent(reqCode, flags, intent)
    }

    @Test
    fun end() {
        journey.end()
        Mockito.verify(visitor)
            .end()
    }

    @Test
    fun visit() {
        Assertions.assertEquals(journey, journey.visit())
        Mockito.verify(visitor)
            .visitActivity(intent)
    }

    @Test
    fun visitForResult() {
        val reqCode = 1
        Assertions.assertEquals(journey, journey.visitForResult(reqCode))
        Mockito.verify(visitor)
            .visitForResult(reqCode, intent)
    }
}