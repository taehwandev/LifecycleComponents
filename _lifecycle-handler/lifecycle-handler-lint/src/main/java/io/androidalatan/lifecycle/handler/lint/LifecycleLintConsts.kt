package io.androidalatan.lifecycle.handler.lint

object LifecycleLintConsts {
    val ANNOTATIONS = listOf(
        "io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy",
        "io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause",
        "io.androidalatan.lifecycle.handler.annotations.async.StartedToStop",
    )

    val RX_JAVA = listOf(
        "io.reactivex.rxjava3.core.Observable",
        "io.reactivex.rxjava3.core.Single",
        "io.reactivex.rxjava3.core.Maybe",
        "io.reactivex.rxjava3.core.Flowable",
        "io.reactivex.rxjava3.core.Completable",
    )

    val COROUTINE = listOf(
        "kotlinx.coroutines.flow.Flow"
    )
}