package io.androidalatan.router.api

import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.Journey

interface JourneyGuidance {
    fun create(): Journey
}

interface ComponentJourneyGuidance : JourneyGuidance {
    override fun create(): ComponentJourney
}