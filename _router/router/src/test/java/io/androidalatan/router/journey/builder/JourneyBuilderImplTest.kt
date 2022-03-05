package io.androidalatan.router.journey.builder

import android.app.Service
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class JourneyBuilderImplTest {

    private val bundleData = Mockito.mock(BundleData::class.java)
    private val visitor = Mockito.mock(Visitor::class.java)
    private val journeyBuilder = JourneyBuilderImpl(visitor, bundleData)

    @Test
    fun activity() {
        val destination = FragmentActivity::class.java
        val activityJourneyBuilder = journeyBuilder.activity(destination)
        Assertions.assertTrue(activityJourneyBuilder is ActivityJourneyBuilderImpl)
    }

    @Test
    fun service() {
        val destination = Service::class.java
        val activityJourneyBuilder = journeyBuilder.service(destination)
        Assertions.assertTrue(activityJourneyBuilder is ServiceJourneyBuilderImpl)
    }

    @Test
    fun fragment() {
        val fragmentJourneyBuilder = JourneyBuilderImpl(Mockito.mock(Visitor::class.java), bundleData).fragment(Fragment::class.java)
        Assertions.assertTrue(fragmentJourneyBuilder is FragmentJourneyBuilderImpl)
    }

    @Test
    fun activityIntent() {
        val activityIntent = journeyBuilder.activityIntent()
        Assertions.assertTrue(activityIntent is ActivityIntentJourneyBuilderImpl)

    }
}