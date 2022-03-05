package io.androidalatan.router.api.journey.builder

import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.journey.ComponentJourney

interface ServiceJourneyBuilder : BaseJourneyBuilder<ComponentJourney> {
    fun putData(body: (BundleData) -> Unit): ServiceJourneyBuilder
    fun setAction(body: () -> String): ServiceJourneyBuilder
    fun setData(body: () -> Uri): ServiceJourneyBuilder
    override fun build(): ComponentJourney
}