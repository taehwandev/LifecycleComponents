package io.androidalatan.lifecycle.handler.compose.cache

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
inline fun <reified T> cached(): T {
    val cacheStoreOwner = LocalComposeComposeCacheOwner.current
    return remember { cacheStoreOwner.cached(T::class.qualifiedName) as T }
}