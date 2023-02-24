package io.androidalatan.lifecycle.handler.lint

object LifecycleLintConsts {
    val ANNOTATIONS = listOf(
        "io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy",
        "io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause",
        "io.androidalatan.lifecycle.handler.annotations.async.StartedToStop",
    )

    val COROUTINE = listOf(
        "kotlinx.coroutines.flow.Flow"
    )
}