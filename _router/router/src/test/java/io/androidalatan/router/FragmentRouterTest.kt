package io.androidalatan.router

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import io.androidalatan.router.api.DeepLinkJourneyGuidance
import io.androidalatan.router.api.GuidanceBuilderFactory
import io.androidalatan.router.api.JourneyFinder
import io.androidalatan.router.api.JourneyGuidance
import io.androidalatan.router.api.journey.Journey
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(value = [Fragment::class, FragmentActivity::class, DialogFragment::class])
class FragmentRouterTest {

    private val fragment = PowerMockito.mock(Fragment::class.java)
    private var activity: FragmentActivity = PowerMockito.mock(FragmentActivity::class.java)
    private val router = FragmentRouter(lazy { fragment })

    @Test
    fun `dismiss dialog fragment`() {
        val fragment = PowerMockito.mock(DialogFragment::class.java)
        val router = FragmentRouter(lazy { fragment })

        router.end()
        verify(fragment).dismissAllowingStateLoss()
    }

    @Test
    fun end() {
        whenever(fragment.activity)
            .thenReturn(activity)

        whenever(fragment.requireActivity())
            .thenReturn(activity)

        router.end()

        Mockito.verify(activity)
            .finish()
    }

    @Test
    fun `find class`() {
        val journeyGuidance = Mockito.mock(JourneyGuidance::class.java)
        JourneyMapper.setJourneys(mapOf(JourneyGuidance::class.java to GuidanceBuilderFactory { journeyGuidance }))

        Assertions.assertEquals(journeyGuidance, router.findOrNull(JourneyGuidance::class))
    }

    @Test
    fun `find uri`() {
        val intent = mock<Intent>()
        val journey = mock<Journey>()
        val journeyBuilderFactory = GuidanceBuilderFactory { MockJourneyGuidance(journey, intent) }
        JourneyMapper.setJourneys(mapOf(JourneyMapperTest.MockJourneyGuidance::class.java to journeyBuilderFactory))

        val deeplinkJourney = router.findByUri(mock<Uri>())
        Assertions.assertEquals(1, deeplinkJourney.size)
        Assertions.assertEquals(intent, deeplinkJourney[0])
    }

    class MockJourneyGuidance(private val journey: Journey, private val intent: Intent) : JourneyGuidance, DeepLinkJourneyGuidance {
        override fun create() = journey

        override fun handle(uri: Uri, journeyFinder: JourneyFinder) = listOf(intent)
    }

}