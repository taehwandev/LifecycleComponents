package io.androidalatan.router.assertion

import io.androidalatan.router.api.GuidanceBuilderFactory
import io.androidalatan.router.api.JourneyGuidance
import io.androidalatan.router.api.journey.builder.JourneyBuilder

class MockGuidanceBuilderFactory : GuidanceBuilderFactory {

    override fun create(journeyBuilder: JourneyBuilder): JourneyGuidance {
        return MockJourneyGuidance()
    }
}