package io.androidalatan.lifecycle.handler.lint

object TestUtils {

    fun annotationClass(): String {
        return """
            package io.androidalatan.lifecycle.handler.annotations.async

            annotation class StartedToStop

        """
    }

    fun coroutineInterface(): String {
        return """
           package kotlinx.coroutines.flow
             
           interface Flow<T>
        """
    }
}