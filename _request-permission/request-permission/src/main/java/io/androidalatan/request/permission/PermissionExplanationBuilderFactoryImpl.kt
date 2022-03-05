package io.androidalatan.request.permission

import android.content.Context
import androidx.fragment.app.FragmentActivity
import io.androidalatan.lifecycle.lazyprovider.LazyProvider
import io.androidalatan.request.permission.api.PermissionExplanationBuilder
import io.androidalatan.request.permission.api.PermissionExplanationBuilderFactory
import java.lang.ref.WeakReference

class PermissionExplanationBuilderFactoryImpl(context: LazyProvider<FragmentActivity>) :
    PermissionExplanationBuilderFactory {
    private val contextRef by lazy {
        WeakReference(context.value as Context)
    }

    override fun explanationBuilder(): PermissionExplanationBuilder {
        return PermissionExplanationBuilderImpl(contextRef)
    }
}