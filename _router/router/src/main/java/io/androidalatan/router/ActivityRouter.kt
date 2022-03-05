package io.androidalatan.router

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.FragmentActivity
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.DeepLinkJourneyGuidance
import io.androidalatan.router.api.JourneyFinder
import io.androidalatan.router.api.JourneyGuidance
import io.androidalatan.router.api.Router
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.JourneyBuilder
import io.androidalatan.router.journey.JourneyGroup
import io.androidalatan.router.journey.builder.JourneyBuilderImpl
import io.androidalatan.router.visitor.ActivityVisitor
import kotlin.reflect.KClass

class ActivityRouter(
    private val activity: Lazy<FragmentActivity>,
    private val visitor: Lazy<Visitor> = lazy { ActivityVisitor(activity.value) }
) : Router {

    override fun end() {
        visitor.value.end()
    }

    override fun end(resultCode: Int, uriData: String, bundleBody: (BundleData) -> Unit) {
        visitor.value.end(resultCode, uriData, bundleBody)
    }

    override fun restart() {
        (visitor.value as? ActivityVisitor)?.restart() ?: throw IllegalStateException("ActivityRouter requires ActivityVisitor")
    }

    override fun activityIntents(vararg intents: Intent): ComponentJourney {
        return JourneyGroup(visitor.value, *intents)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : JourneyGuidance> findOrNull(klazz: KClass<out T>): T? {
        return JourneyMapper.getJourneyOrNull(klazz.java)
            ?.create(JourneyBuilderImpl(visitor.value)) as? T
    }

    override fun newJourneyBuilder(): JourneyBuilder = JourneyBuilderImpl(visitor.value)

    override fun journeyFinder(): JourneyFinder {
        return JourneyFinder {
            val journeyGuidance = findOrNull(it)
            requireNotNull(journeyGuidance) { "JourneyFinder cannot find ${it.simpleName} " }
            journeyGuidance.create()
        }
    }

    override fun findByUri(uri: Uri): List<Intent> {
        val journeyBuilder = JourneyBuilderImpl(visitor.value)
        return JourneyMapper.lookUpForDeepLink()
            .map { guidanceBuilderFactory ->
                (guidanceBuilderFactory.create(journeyBuilder) as DeepLinkJourneyGuidance).handle(uri, journeyFinder())
            }
            .flatten()
    }

}