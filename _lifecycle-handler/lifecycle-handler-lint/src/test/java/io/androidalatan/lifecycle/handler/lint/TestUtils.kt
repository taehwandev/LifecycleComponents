package io.androidalatan.lifecycle.handler.lint

object TestUtils {

    fun annotationClass(): String {
        return """
            package io.androidalatan.lifecycle.handler.annotations.async

            annotation class StartedToStop

        """
    }

    fun rxJavaInterface(): String {
        return """
           package io.reactivex.rxjava3.core
             
           interface Observable<T>
        """
    }

    fun coroutineInterface(): String {
        return """
           package kotlinx.coroutines.flow
             
           interface Flow<T>
        """
    }
}