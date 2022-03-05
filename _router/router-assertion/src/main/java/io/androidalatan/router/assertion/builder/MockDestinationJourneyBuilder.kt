package io.androidalatan.router.assertion.builder

import android.content.Intent
import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.bundle.assertion.MapBundleData
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.ActivityIntentJourneyBuilder
import io.androidalatan.router.api.journey.builder.ActivityJourneyBuilder
import io.androidalatan.router.api.journey.builder.ServiceJourneyBuilder
import io.androidalatan.router.assertion.MockComponentJourney

class MockDestinationJourneyBuilder : ActivityJourneyBuilder, ServiceJourneyBuilder, ActivityIntentJourneyBuilder {
    var destination: Class<*>? = null
    var action: String = ""
    var mapBundleData = MapBundleData(hashMapOf())
    var buildCount = 0
    var flag = 0
    var uri: Uri? = null
    var builtJourney: MockComponentJourney? = null
    var type: String? = null
    var target: Int = 0
    var intent: Intent? = null

    override fun setIntent(body: () -> Intent): ActivityIntentJourneyBuilder {
        intent = body()
        return this
    }

    override fun putData(body: (BundleData) -> Unit): MockDestinationJourneyBuilder {
        body(mapBundleData)
        return this
    }

    override fun setAction(body: () -> String): MockDestinationJourneyBuilder {
        this.action = body()
        return this
    }

    override fun addFlag(body: () -> Int): MockDestinationJourneyBuilder {
        flag = flag or body()
        return this
    }

    override fun setData(body: () -> Uri): MockDestinationJourneyBuilder {
        uri = body()
        return this
    }

    override fun setType(body: () -> String): ActivityIntentJourneyBuilder {
        type = body()
        return this
    }

    override fun build(): ComponentJourney {
        buildCount++
        return MockComponentJourney().apply { builtJourney = this }
    }
}