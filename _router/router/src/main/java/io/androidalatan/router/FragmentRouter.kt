package io.androidalatan.router

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.DeepLinkJourneyGuidance
import io.androidalatan.router.api.JourneyFinder
import io.androidalatan.router.api.JourneyGuidance
import io.androidalatan.router.api.Router
import io.androidalatan.router.api.journey.ComponentJourney
import io.androidalatan.router.api.journey.builder.JourneyBuilder
import io.androidalatan.router.journey.JourneyGroup
import io.androidalatan.router.journey.builder.JourneyBuilderImpl
import io.androidalatan.router.visitor.ActivityVisitor
import io.androidalatan.router.visitor.FragmentVisitor
import kotlin.reflect.KClass

class FragmentRouter(private val fragment: Lazy<Fragment>) : Router {

    private val fragmentVisitor by lazy { FragmentVisitor(fragment.value) }

    private fun dismissDialog(dialogFragment: DialogFragment) {
        dialogFragment.dismissAllowingStateLoss()
    }

    override fun end() {
        if (fragment.value is DialogFragment) {
            dismissDialog(fragment.value as DialogFragment)
        } else {
            fragmentVisitor.end()
        }
    }

    override fun end(resultCode: Int, uriData: String, bundleBody: (BundleData) -> Unit) {
        fragmentVisitor.end(resultCode, uriData, bundleBody)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : JourneyGuidance> findOrNull(klazz: KClass<out T>): T? {
        return JourneyMapper.getJourneyOrNull(klazz.java)
            ?.create(JourneyBuilderImpl(fragmentVisitor)) as? T
    }

    override fun journeyFinder(): JourneyFinder {
        return JourneyFinder {
            val journeyGuidance = findOrNull(it)
            requireNotNull(journeyGuidance) { "JourneyFinder cannot find ${it.simpleName} " }
            journeyGuidance.create()
        }
    }

    override fun findByUri(uri: Uri): List<Intent> {
        val journeyBuilder = JourneyBuilderImpl(fragmentVisitor)
        return JourneyMapper.lookUpForDeepLink()
            .map { guidanceBuilderFactory ->
                (guidanceBuilderFactory.create(journeyBuilder) as DeepLinkJourneyGuidance).handle(uri, journeyFinder())
            }
            .flatten()
    }

    override fun activityIntents(vararg intents: Intent): ComponentJourney =
        JourneyGroup(ActivityVisitor(fragment.value.requireActivity()), *intents)

    override fun newJourneyBuilder(): JourneyBuilder = JourneyBuilderImpl(fragmentVisitor)

    override fun restart() {
        throw UnsupportedOperationException("restart() is not for Fragment")
    }

}