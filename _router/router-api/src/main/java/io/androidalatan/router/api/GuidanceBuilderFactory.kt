package io.androidalatan.router.api

import io.androidalatan.router.api.journey.builder.JourneyBuilder

fun interface GuidanceBuilderFactory {

    fun create(journeyBuilder: JourneyBuilder): JourneyGuidance
}