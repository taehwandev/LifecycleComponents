package io.androidalatan.router

import io.androidalatan.router.api.DeepLinkJourneyGuidance
import io.androidalatan.router.api.GuidanceBuilderFactory
import io.androidalatan.router.api.JourneyGuidance

object JourneyMapper {
    private var map: MutableMap<Class<out JourneyGuidance>, GuidanceBuilderFactory> = mutableMapOf()

    fun setJourneys(map: Map<Class<out JourneyGuidance>, GuidanceBuilderFactory>) {
        JourneyMapper.map.putAll(map)
    }

    fun getJourneyOrNull(journeyKlass: Class<*>): GuidanceBuilderFactory? {
        return map[journeyKlass]
    }

    fun lookUpForDeepLink(): List<GuidanceBuilderFactory> {
        return map.keys.filter { key ->
            DeepLinkJourneyGuidance::class.java.isAssignableFrom(key)
        }
            .map { map.getValue(it) }
    }
}