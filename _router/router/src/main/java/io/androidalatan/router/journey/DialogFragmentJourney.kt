package io.androidalatan.router.journey

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import io.androidalatan.router.api.Visitor
import io.androidalatan.router.api.journey.FragmentJourney

class DialogFragmentJourney(
    private val visitor: Visitor,
    private val fragment: Class<out DialogFragment>,
    private val arguments: Bundle,
) : FragmentJourney {

    override fun end() {
        visitor.end()
    }

    override fun visit(): DialogFragmentJourney {
        val builtFragment = visitor.buildFragment(fragment)
        builtFragment.arguments = arguments
        visitor.visitFragment(0, builtFragment)
        return this
    }
}