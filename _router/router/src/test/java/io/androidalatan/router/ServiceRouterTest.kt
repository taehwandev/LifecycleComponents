package io.androidalatan.router

import android.app.Service
import android.content.Intent
import android.net.Uri
import io.androidalatan.router.api.DeepLinkJourneyGuidance
import io.androidalatan.router.api.GuidanceBuilderFactory
import io.androidalatan.router.api.JourneyFinder
import io.androidalatan.router.api.JourneyGuidance
import io.androidalatan.router.api.journey.Journey
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class ServiceRouterTest {

    private val service = Mockito.mock(Service::class.java)
    private val router = ServiceRouter(lazy { service })

    @Test
    fun end() {
        router.end()
        Mockito.verify(service)
            .stopSelf()
    }

    @Test
    fun `end resultCode`() {
        router.end(1231, "") {}
        Mockito.verify(service)
            .stopSelf()
    }

    @Test
    fun `find class`() {
        val journeyGuidance = Mockito.mock(JourneyGuidance::class.java)
        JourneyMapper.setJourneys(mapOf(JourneyGuidance::class.java to GuidanceBuilderFactory { journeyGuidance }))

        Assertions.assertEquals(journeyGuidance, router.findOrNull(JourneyGuidance::class))
    }

    @Test
    fun `find uri`() {
        val intent = mock<Intent>()
        val journey = mock<Journey>()
        val journeyBuilderFactory = GuidanceBuilderFactory { MockJourneyGuidance(journey, intent) }
        JourneyMapper.setJourneys(mapOf(JourneyMapperTest.MockJourneyGuidance::class.java to journeyBuilderFactory))

        val deeplinkJourney = router.findByUri(mock<Uri>())
        Assertions.assertEquals(1, deeplinkJourney.size)
        Assertions.assertEquals(intent, deeplinkJourney[0])
    }

    class MockJourneyGuidance(private val journey: Journey, private val intent: Intent) : JourneyGuidance, DeepLinkJourneyGuidance {
        override fun create() = journey

        override fun handle(uri: Uri, journeyFinder: JourneyFinder) = listOf(intent)
    }

}