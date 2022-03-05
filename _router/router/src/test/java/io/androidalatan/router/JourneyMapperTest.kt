package io.androidalatan.router

import io.androidalatan.router.api.DeepLinkJourneyGuidance
import io.androidalatan.router.api.GuidanceBuilderFactory
import io.androidalatan.router.api.JourneyGuidance
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class JourneyMapperTest {

    @Test
    fun `setJourneys and getJourney`() {
        val journeyBuilderFactory = Mockito.mock(GuidanceBuilderFactory::class.java)
        JourneyMapper.setJourneys(mapOf(MockJourneyGuidance::class.java to journeyBuilderFactory))

        Assertions.assertEquals(journeyBuilderFactory, JourneyMapper.getJourneyOrNull(MockJourneyGuidance::class.java))

    }

    @Test
    fun lookUpForDeepLink() {
        val journeyBuilderFactory = Mockito.mock(GuidanceBuilderFactory::class.java)
        JourneyMapper.setJourneys(mapOf(MockJourneyGuidance::class.java to journeyBuilderFactory))

        val lookUpForDeepLink = JourneyMapper.lookUpForDeepLink()
        Assertions.assertEquals(1, lookUpForDeepLink.size)
        Assertions.assertEquals(journeyBuilderFactory, lookUpForDeepLink[0])

    }

    interface MockJourneyGuidance : JourneyGuidance, DeepLinkJourneyGuidance
}