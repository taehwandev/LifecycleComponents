package io.androidalatan.router.api

import io.androidalatan.router.api.journey.Journey
import kotlin.reflect.KClass

fun interface JourneyFinder {
    fun find(klazz: KClass<out JourneyGuidance>): Journey
}