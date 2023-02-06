package io.androidalatan.backkey.handler

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class ActivityBackKeyObserverTest {

    @Test
    fun initIfNeed() {
        val activity = mock<ComponentActivity>()
        val dispatcher = mock<OnBackPressedDispatcher>()
        val activityBackKeyObserver = ActivityBackKeyObserver(activity)

        whenever(activity.onBackPressedDispatcher).thenReturn(dispatcher)

        activityBackKeyObserver.initIfNeed { emptyList() }

        Assertions.assertNotNull(activityBackKeyObserver.onBackPressedCallback)
        Assertions.assertTrue(activityBackKeyObserver.onBackPressedCallback!!.isEnabled)
        verify(dispatcher).addCallback(activity, activityBackKeyObserver.onBackPressedCallback!!)
        verify(activity).onBackPressedDispatcher

        activityBackKeyObserver.deinit()
        Assertions.assertFalse(activityBackKeyObserver.onBackPressedCallback!!.isEnabled)
    }

}