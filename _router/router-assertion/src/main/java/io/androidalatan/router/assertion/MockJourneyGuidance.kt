package io.androidalatan.router.assertion

import io.androidalatan.router.api.JourneyGuidance
import io.androidalatan.router.api.journey.Journey

class MockJourneyGuidance : JourneyGuidance {
    var createCount = 0
    override fun create(): Journey {
        createCount++
        return MockComponentJourney()
    }
}