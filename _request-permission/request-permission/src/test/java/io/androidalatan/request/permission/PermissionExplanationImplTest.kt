package io.androidalatan.request.permission

import android.content.Context
import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.lang.ref.WeakReference

class PermissionExplanationImplTest {

    @Test
    fun `show no context`() {
        PermissionExplanationImpl(WeakReference<Context>(null), Mockito.mock(
            PermissionExplanationBuilder::class.java))
            .show {}
        // It needs to verify no AlertDialog instance creation via PowerMock.
    }

    @Disabled("It requires Roboletric")
    @Test
    fun `show happy path`() {
        // do nothing
    }
}