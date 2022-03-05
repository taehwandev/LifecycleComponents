package io.androidalatan.backkey.handler

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedDispatcher
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(value = [OnBackPressedDispatcher::class, ComponentActivity::class])
class ActivityBackKeyObserverTest {

    @Test
    fun initIfNeed() {
        val activity = PowerMockito.mock(ComponentActivity::class.java)
        val dispatcher = mock<OnBackPressedDispatcher>()
        val activityBackKeyObserver = ActivityBackKeyObserver(activity)

        PowerMockito.`when`(activity.onBackPressedDispatcher)
            .thenReturn(dispatcher)

        activityBackKeyObserver.initIfNeed { emptyList() }

        Assertions.assertNotNull(activityBackKeyObserver.onBackPressedCallback)
        Assertions.assertTrue(activityBackKeyObserver.onBackPressedCallback!!.isEnabled)
        verify(dispatcher).addCallback(activity, activityBackKeyObserver.onBackPressedCallback!!)

        activityBackKeyObserver.deinit()
        Assertions.assertFalse(activityBackKeyObserver.onBackPressedCallback!!.isEnabled)
    }

}