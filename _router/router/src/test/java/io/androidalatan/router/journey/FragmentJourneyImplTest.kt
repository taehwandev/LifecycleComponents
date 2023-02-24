package io.androidalatan.router.journey

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.androidalatan.router.api.Visitor
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class FragmentJourneyImplTest {

    private val visitor = mock<Visitor>()

    private val bundle = mock<Bundle>()

    private val id = 1

    private val journey = FragmentJourneyImpl(visitor, id, Fragment::class.java, bundle)

    @Test
    fun end() {
        journey.end()
        verify(visitor).end()
    }

    @Test
    fun visit() {
        val fragment = mock<Fragment>()
        whenever(visitor.buildFragment(any())).thenReturn(fragment)
        journey.visit()
        verify(fragment).arguments = bundle
        verify(visitor).replaceFragment(id, fragment)
    }
}