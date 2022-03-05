package io.androidalatan.router.journey.builder

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import io.androidalatan.router.journey.DialogFragmentJourney
import io.androidalatan.router.journey.FragmentJourneyImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock

class FragmentJourneyBuilderImplTest {

    @Test
    fun `build dialogfragment`() {
        val journey = FragmentJourneyBuilderImpl(MockDialogFragment::class.java, mock())
            .build()

        Assertions.assertTrue(journey is DialogFragmentJourney)
    }

    @Test
    fun `build fragment`() {
        val journey = FragmentJourneyBuilderImpl(MockFragment::class.java, mock())
            .build()

        Assertions.assertTrue(journey is FragmentJourneyImpl)
    }
}

class MockDialogFragment : DialogFragment()
class MockFragment : Fragment()