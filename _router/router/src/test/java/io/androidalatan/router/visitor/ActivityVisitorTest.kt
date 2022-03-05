package io.androidalatan.router.visitor

import android.content.Intent
import android.os.Build
import androidx.fragment.app.FragmentActivity
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.eq
import org.mockito.kotlin.verify
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O])
@RunWith(RobolectricTestRunner::class)
class ActivityVisitorTest {

    private val activity = Mockito.mock(FragmentActivity::class.java)
    private val visitor = ActivityVisitor(activity)

    @Test
    fun end() {
        visitor.end()
        Mockito.verify(activity)
            .finish()
    }

    @Test
    fun `end resultCode`() {
        val resultCode = 3123
        val uriData = "hello://"
        val key = "key-1"
        val value = "value-1"
        visitor.end(resultCode, uriData) {
            it.setString(key, value)
        }

        val argumentCaptor = argumentCaptor<Intent>()

        verify(activity).setResult(eq(resultCode), argumentCaptor.capture())
        verify(activity).finish()

        val intent = argumentCaptor.firstValue
        Assertions.assertEquals(uriData, intent.dataString)
        Assertions.assertTrue(intent.hasExtra(key))
        Assertions.assertEquals(value, intent.getStringExtra(key))

    }

    @Test
    fun visit() {
        val intent = Mockito.mock(Intent::class.java)
        visitor.visitActivity(intent)
        Mockito.verify(activity)
            .startActivity(intent)
    }

    @Test
    fun visitGroup() {
        val intent = Mockito.mock(Intent::class.java)
        visitor.visitGroup(arrayOf(intent))
        Mockito.verify(activity)
            .startActivities(arrayOf(intent))
    }

    @Test
    fun visitForResult() {
        val intent = Mockito.mock(Intent::class.java)
        val reqCode = 1203
        visitor.visitForResult(reqCode, intent)
        Mockito.verify(activity)
            .startActivityForResult(intent, reqCode)
    }

    @Test
    fun visitService() {
        val intent = Mockito.mock(Intent::class.java)
        visitor.visitService(intent)
        Mockito.verify(activity)
            .startService(intent)
    }

    @Test
    fun restart() {
        visitor.restart()
        verify(activity).recreate()
    }
}