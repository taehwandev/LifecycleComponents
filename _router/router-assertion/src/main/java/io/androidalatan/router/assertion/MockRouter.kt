package io.androidalatan.router.assertion

import android.content.Intent
import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.bundle.assertion.MapBundleData
import io.androidalatan.router.api.JourneyFinder
import io.androidalatan.router.api.JourneyGuidance
import io.androidalatan.router.api.Router
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.JourneyBuilder
import io.androidalatan.router.assertion.builder.MockJourneyBuilder
import kotlin.reflect.KClass

class MockRouter : Router {
    var endCount = 0
    var resultCode = 0
    var uriData: String = ""
    var bundleData = MapBundleData(hashMapOf())
    var lastIntent: Intent? = null
    var lastIntentGroup: Array<out Intent> = emptyArray()
    var mapOfJourneyGuidance = hashMapOf<KClass<out JourneyGuidance>, JourneyGuidance>()
    var mapOfDeepLink = hashMapOf<Uri, List<Intent>>()
    var findCount = hashMapOf<KClass<out JourneyGuidance>, Int>()
    var lastJourneyBuilder: MockJourneyBuilder? = null

    var restartCount = 0

    override fun end() {
        endCount++
    }

    override fun restart() {
        restartCount++
    }

    override fun end(resultCode: Int, uriData: String, bundleBody: (BundleData) -> Unit) {
        endCount++
        this.uriData = uriData
        this.resultCode = resultCode
        bundleBody(bundleData)
    }

    override fun activityIntents(vararg intent: Intent): ComponentJourney {
        lastIntentGroup = intent
        return MockComponentJourney()
    }

    override fun <T : JourneyGuidance> findOrNull(klazz: KClass<out T>): T? {
        findCount[klazz] = findCount.getOrPut(klazz) { 0 } + 1
        return mapOfJourneyGuidance[klazz] as? T
    }

    override fun journeyFinder(): JourneyFinder {
        return JourneyFinder {
            mapOfJourneyGuidance.getValue(it)
                .create()
        }
    }

    override fun newJourneyBuilder(): JourneyBuilder {
        val mockJourneyBuilder = MockJourneyBuilder()
        lastJourneyBuilder = mockJourneyBuilder
        return mockJourneyBuilder
    }

    fun <T : JourneyGuidance> putJourneyGuidance(klass: KClass<out T>, journeyGuidance: T) {
        mapOfJourneyGuidance[klass] = journeyGuidance
    }

    fun putDeepLinkIntent(uri: Uri, intents: List<Intent>) {
        mapOfDeepLink[uri] = intents
    }

    override fun findByUri(uri: Uri): List<Intent> {
        return mapOfDeepLink[uri] ?: emptyList()
    }
}