package io.androidalatan.router.journey

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import io.androidalatan.router.api.Visitor
import org.junit.jupiter.api.Test

class DialogFragmentJourneyTest {

    private val visitor = mock<Visitor>()
    private val bundle = mock<Bundle>()
    private val journey = DialogFragmentJourney(visitor, DialogFragment::class.java, bundle)

    @Test
    fun end() {
        journey.end()
        verify(visitor).end()
    }

    @Test
    fun visit() {
        val mock = mock<Fragment>()
        whenever(visitor.buildFragment(any())).thenReturn(mock)
        journey.visit()
        verify(mock).arguments = bundle
        verify(visitor).visitFragment(0, mock)

    }
}