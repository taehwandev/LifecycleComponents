package io.androidalatan.router.web.api

import io.androidalatan.router.api.ComponentJourneyGuidance

interface ChromeWebJourneyGuidance : ComponentJourneyGuidance {
    fun url(url: String): ChromeWebJourneyGuidance
}