package io.androidalatan.router.journey

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.FragmentJourney

class FragmentJourneyImpl(
    private val visitor: Visitor,
    private val containerId: Int,
    private val fragment: Class<out Fragment>,
    private val arguments: Bundle,
) : FragmentJourney {

    override fun end() {
        visitor.end()
    }

    override fun visit(): FragmentJourneyImpl {
        val builtFragment = visitor.buildFragment(fragment)
        builtFragment.arguments = arguments
        visitor.replaceFragment(
            containerId = containerId,
            fragment = builtFragment
        )
        return this
    }
}