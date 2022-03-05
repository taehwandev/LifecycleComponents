package io.androidalatan.router.journey.builder

import android.app.Activity
import android.net.Uri
import io.androidalatan.bundle.api.BundleData
import io.androidalatan.router.api.Visitor
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ActivityJourneyBuilderImplTest {

    private val bundleData = Mockito.mock(BundleData::class.java)
    private val visitor = Mockito.mock(Visitor::class.java)

    private val builder = ActivityJourneyBuilderImpl(Activity::class.java, visitor, bundleData)

    @Test
    fun putData() {
        val key = "title"
        val value = "title-1"
        builder.putData {
            it.setString(key, value)
        }

        verify(bundleData).setString(key, value)
    }

    @Test
    fun setAction() {
        val action = "Hello"
        builder.setAction { action }
        Assertions.assertEquals(action, builder.intentAction)
    }

    @Test
    fun addFlag() {
        val flag = 1234
        builder.addFlag { flag }
        Assertions.assertEquals(flag, builder.intentFlag)
    }

    @Test
    fun setData() {
        val uri = mock<Uri>()
        builder.setData { uri }
        Assertions.assertEquals(uri, builder.uri)
    }

    @Disabled("It requires Roboletric or Powermock")
    @Test
    fun build() {
        // do nothing
    }
}