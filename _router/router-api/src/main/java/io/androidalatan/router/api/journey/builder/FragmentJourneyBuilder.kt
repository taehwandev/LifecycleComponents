package io.androidalatan.router.api.journey.builder

import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.journey.FragmentJourney

interface FragmentJourneyBuilder : BaseJourneyBuilder<FragmentJourney> {
    fun putData(body: (BundleData) -> Unit): FragmentJourneyBuilder
    fun target(containerId: Int): FragmentJourneyBuilder
    override fun build(): FragmentJourney
}