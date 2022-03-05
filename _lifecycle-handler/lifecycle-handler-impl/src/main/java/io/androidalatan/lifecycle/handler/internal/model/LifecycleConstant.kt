package io.androidalatan.lifecycle.handler.internal.model

import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.annotations.async.StartedToStop
import io.androidalatan.lifecycle.handler.annotations.sync.OnCreated
import io.androidalatan.lifecycle.handler.annotations.sync.OnDestroy
import io.androidalatan.lifecycle.handler.annotations.sync.OnPause
import io.androidalatan.lifecycle.handler.annotations.sync.OnResumed
import io.androidalatan.lifecycle.handler.annotations.sync.OnStarted
import io.androidalatan.lifecycle.handler.annotations.sync.OnStop

internal object LifecycleConstant {
    val GROUP_OF_ASYNC_ANNOTATION = hashSetOf(CreatedToDestroy::class, ResumedToPause::class, StartedToStop::class)
    val GROUP_OF_SYNC_ANNOTATION =
        hashSetOf(
            OnCreated::class, OnStarted::class, OnResumed::class, OnPause::class, OnStop::class,
            OnDestroy::class)
}