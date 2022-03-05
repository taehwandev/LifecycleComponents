package io.androidalatan.router.api.journey.builder

import io.androidalatan.router.api.journey.Journey

sealed interface BaseJourneyBuilder<T : Journey> {
    fun build(): T
}