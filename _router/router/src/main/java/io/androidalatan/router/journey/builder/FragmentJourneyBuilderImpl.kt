package io.androidalatan.router.journey.builder

import android.os.Bundle
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.androidalatan.bundle.BundleDataImpl
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.FragmentJourney
import io.androidalatan.router.api.journey.builder.FragmentJourneyBuilder
import io.androidalatan.router.journey.DialogFragmentJourney
import io.androidalatan.router.journey.FragmentJourneyImpl

class FragmentJourneyBuilderImpl(
    private val destination: Class<out Fragment>,
    private val visitor: Visitor,
    private val bundleData: BundleData = BundleDataImpl(Bundle())
) : FragmentJourneyBuilder {

    @VisibleForTesting
    var containerId: Int = INVALID_CONTAINER_ID

    override fun putData(body: (BundleData) -> Unit): FragmentJourneyBuilder {
        body(bundleData)
        return this
    }

    override fun target(containerId: Int): FragmentJourneyBuilder {
        this.containerId = containerId
        return this
    }

    override fun build(): FragmentJourney {

        @Suppress("UNCHECKED_CAST")
        return when {
            DialogFragment::class.java.isAssignableFrom(destination) -> DialogFragmentJourney(
                visitor = visitor,
                fragment = destination as Class<DialogFragment>,
                arguments = bundleData.rawBundle()
            )
            else -> FragmentJourneyImpl(
                visitor = visitor,
                containerId = containerId,
                fragment = destination,
                arguments = bundleData.rawBundle()
            )
        }

    }

    companion object {
        private const val INVALID_CONTAINER_ID = -1
    }
}