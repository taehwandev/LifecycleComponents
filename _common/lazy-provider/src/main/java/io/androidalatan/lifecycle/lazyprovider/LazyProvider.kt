package io.androidalatan.lifecycle.lazyprovider

class LazyProvider<T>(private var _value: T? = null) : Lazy<T> {
    override val value: T
        get() {
            val localValue = _value
            requireNotNull(localValue) { "value is null" }
            return localValue
        }

    fun set(value: T) {
        require(_value == null) { "value is set already" }
        this._value = value
    }

    override fun isInitialized(): Boolean {
        return _value != null
    }
}