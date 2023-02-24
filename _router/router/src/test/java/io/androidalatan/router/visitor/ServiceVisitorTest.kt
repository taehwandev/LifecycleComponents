package io.androidalatan.router.visitor

import android.app.Service
import androidx.fragment.app.Fragment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class ServiceVisitorTest {

    private val service = Mockito.mock(Service::class.java)
    private val visitor = ServiceVisitor(service)

    @Test
    fun end() {
        visitor.end()
        Mockito.verify(service)
            .stopSelf()
    }

    @Test
    fun `end resultCode`() {
        visitor.end(1231, "asd") {}
        verify(service).stopSelf()
    }

    @Test
    fun buildFragment() {
        Assertions.assertThrows(UnsupportedOperationException::class.java) {
            visitor.buildFragment(Fragment::class.java)
        }
    }
}