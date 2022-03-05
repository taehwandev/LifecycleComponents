package io.androidalatan.router.assertion.builder

import android.content.Intent
import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.bundle.assertion.MapBundleData
import io.androidalatan.router.api.journey.FragmentJourney
import io.androidalatan.router.api.journey.builder.FragmentJourneyBuilder
import io.androidalatan.router.assertion.MockFragmentJourney

class MockDestinationFragmentJourneyBuilder : FragmentJourneyBuilder {
    var destination: Class<*>? = null
    var action: String = ""
    var mapBundleData = MapBundleData(hashMapOf())
    var buildCount = 0
    var flag = 0
    var uri: Uri? = null
    var builtJourney: MockFragmentJourney? = null
    var type: String? = null
    var target: Int = 0
    var intent: Intent? = null

    override fun target(containerId: Int): FragmentJourneyBuilder {
        this.target = containerId
        return this
    }

    override fun putData(body: (BundleData) -> Unit): MockDestinationFragmentJourneyBuilder {
        body(mapBundleData)
        return this
    }

    override fun build(): FragmentJourney {
        buildCount++
        return MockFragmentJourney().apply { builtJourney = this }
    }
}