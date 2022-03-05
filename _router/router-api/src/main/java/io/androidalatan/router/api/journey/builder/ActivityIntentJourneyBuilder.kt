package io.androidalatan.router.api.journey.builder

import android.content.Intent
import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.journey.ComponentJourney

interface ActivityIntentJourneyBuilder : BaseJourneyBuilder<ComponentJourney> {
    fun setIntent(body: () -> Intent): ActivityIntentJourneyBuilder
    fun putData(body: (BundleData) -> Unit): ActivityIntentJourneyBuilder
    fun setAction(body: () -> String): ActivityIntentJourneyBuilder
    fun addFlag(body: () -> Int): ActivityIntentJourneyBuilder
    fun setData(body: () -> Uri): ActivityIntentJourneyBuilder
    fun setType(body: () -> String): ActivityIntentJourneyBuilder
    override fun build(): ComponentJourney
}