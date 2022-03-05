package io.androidalatan.request.permission

import androidx.fragment.app.FragmentActivity
import io.androidalatan.lifecycle.lazyprovider.LazyProvider
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class PermissionExplanationBuilderFactoryImplTest {

    private val factory = PermissionExplanationBuilderFactoryImpl(LazyProvider(Mockito.mock(FragmentActivity::class.java)))

    @Test
    fun explanationBuilder() {
        val explanationBuilder = factory.explanationBuilder()
        Assertions.assertNotNull(explanationBuilder)
        Assertions.assertTrue(explanationBuilder is PermissionExplanationBuilderImpl)
    }
}