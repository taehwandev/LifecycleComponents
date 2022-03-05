package io.androidalatan.router.api.journey.builder

import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.journey.ComponentJourney

interface ActivityJourneyBuilder : BaseJourneyBuilder<ComponentJourney> {
    fun putData(body: (BundleData) -> Unit): ActivityJourneyBuilder
    fun setAction(body: () -> String): ActivityJourneyBuilder
    fun addFlag(body: () -> Int): ActivityJourneyBuilder
    fun setData(body: () -> Uri): ActivityJourneyBuilder
    override fun build(): ComponentJourney
}