package io.androidalatan.router.api

import android.content.Intent
import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.JourneyBuilder
import kotlin.reflect.KClass

interface Router {

    fun end()

    fun end(resultCode: Int, uriData: String = "", bundleBody: (BundleData) -> Unit = {})

    fun <T : JourneyGuidance> findOrNull(klazz: KClass<out T>): T?

    fun journeyFinder(): JourneyFinder

    fun findByUri(uri: Uri): List<Intent>

    fun activityIntents(vararg intents: Intent): ComponentJourney

    fun newJourneyBuilder(): JourneyBuilder

    fun restart()
}