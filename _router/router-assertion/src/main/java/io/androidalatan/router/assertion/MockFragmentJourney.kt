package io.androidalatan.router.assertion

import io.androidalatan.router.api.journey.FragmentJourney

class MockFragmentJourney : FragmentJourney {
    var endCount = 0
    var dismissCount = 0
    var visitCount = 0
    var reqCode = -1

    override fun end() {
        endCount++
    }

    override fun visit(): FragmentJourney {
        visitCount++
        return this
    }
}