package io.androidalatan.lifecycle.handler.compose.cache

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

inline fun <reified T, U> ComposeCacheProvider.composeCached(noinline initialInvoker: (() -> T)? = null): ReadWriteProperty<U, T> {
    val key = T::class.qualifiedName
    initialInvoker?.let { composeCache().save(initialInvoker() as Any) }
    return object : ReadWriteProperty<U, T> {
        override fun getValue(thisRef: U, property: KProperty<*>): T = composeCache()
            .cached(key)

        override fun setValue(thisRef: U, property: KProperty<*>, value: T) =
            composeCache()
                .save(value as Any)
    }
}

interface ComposeCacheProvider {
    fun composeCache(): ComposeCache
}

fun composeCacheProvider(): ComposeCacheProvider {
    return object : ComposeCacheProvider {
        val container = ComposeCacheImpl()
        override fun composeCache(): ComposeCache = container
    }
}

interface ComposeCache {
    fun <T> cached(key: String?): T
    fun cachedValues(): List<Any>
    fun save(obj: Any)
    fun clear()

}

internal class ComposeCacheImpl : ComposeCache {
    val cached = mutableMapOf<String, Any>()

    @Suppress("UNCHECKED_CAST")
    override fun <T> cached(key: String?): T {
        return this.cached[key] as? T ?: throw IllegalStateException("$key isn't cached yet")
    }

    override fun cachedValues(): List<Any> {
        return this.cached.values.toList()
    }

    override fun save(obj: Any) {
        val klazzName = obj::class.qualifiedName ?: throw IllegalStateException("ensure of class name")
        cached[klazzName] = obj
    }

    override fun clear() {
        cached.clear()
    }
}
