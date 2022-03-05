package io.androidalatan.router

import android.app.Service
import android.content.Intent
import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.DeepLinkJourneyGuidance
import io.androidalatan.router.api.JourneyFinder
import io.androidalatan.router.api.JourneyGuidance
import io.androidalatan.router.api.Router
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.JourneyBuilder
import io.androidalatan.router.journey.JourneyGroup
import io.androidalatan.router.journey.builder.JourneyBuilderImpl
import io.androidalatan.router.visitor.ServiceVisitor
import kotlin.reflect.KClass

class ServiceRouter(
    private val service: Lazy<Service>
) : Router {

    private val visitor by lazy { ServiceVisitor(service.value) }

    override fun end() {
        visitor.end()
    }

    override fun end(resultCode: Int, uriData: String, bundleBody: (BundleData) -> Unit) {
        visitor.end()
    }

    override fun activityIntents(vararg intents: Intent): ComponentJourney {
        return JourneyGroup(visitor, *intents)
    }

    override fun newJourneyBuilder(): JourneyBuilder = JourneyBuilderImpl(visitor)

    override fun restart() {
        throw UnsupportedOperationException("restart() is not for Service")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : JourneyGuidance> findOrNull(klazz: KClass<out T>): T? {
        return JourneyMapper.getJourneyOrNull(klazz.java)
            ?.create(JourneyBuilderImpl(visitor)) as? T
    }

    override fun journeyFinder(): JourneyFinder {
        return JourneyFinder {
            val journeyGuidance = findOrNull(it)
            requireNotNull(journeyGuidance) { "JourneyFinder cannot find ${it.simpleName} " }
            journeyGuidance.create()
        }
    }

    override fun findByUri(uri: Uri): List<Intent> {
        val journeyBuilder = JourneyBuilderImpl(visitor)
        return JourneyMapper.lookUpForDeepLink()
            .map { guidanceBuilderFactory ->
                (guidanceBuilderFactory.create(journeyBuilder) as DeepLinkJourneyGuidance).handle(uri, journeyFinder())
            }
            .flatten()
    }

}