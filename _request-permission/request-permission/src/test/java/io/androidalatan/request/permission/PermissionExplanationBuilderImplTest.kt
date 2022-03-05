package io.androidalatan.request.permission

import android.content.Context
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.lang.ref.WeakReference

class PermissionExplanationBuilderImplTest {

    private val builder = PermissionExplanationBuilderImpl(WeakReference(Mockito.mock(Context::class.java)))

    @Test
    fun build() {
        val permissionExplanation = builder.build()
        Assertions.assertNotNull(permissionExplanation)
        Assertions.assertTrue(permissionExplanation is PermissionExplanationImpl)
    }
}