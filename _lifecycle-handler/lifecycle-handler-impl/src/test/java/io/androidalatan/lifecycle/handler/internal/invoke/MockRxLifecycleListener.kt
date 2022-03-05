package io.androidalatan.lifecycle.handler.internal.invoke

import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.assertion.MockLifecycleSource
import io.androidalatan.view.event.api.ViewInteractionStream
import io.reactivex.rxjava3.core.Observable

@Suppress("unused")
class MockRxLifecycleListener : LifecycleListener(MockLifecycleSource()) {
    @JvmField
    var executedUnitCount = 0

    @JvmField
    var executedRxCount = 0

    @CreatedToDestroy
    fun executeUnit() {
        executedUnitCount++
    }

    @CreatedToDestroy
    fun executeRx(@Suppress("UNUSED_PARAMETER") viewInteractionStream: ViewInteractionStream): Observable<Boolean> {
        return Observable.never<Boolean>()
            .startWithItem(true)
            .doOnSubscribe { executedRxCount++ }
    }

    @ResumedToPause
    fun executeRxResume(@Suppress("UNUSED_PARAMETER") viewInteractionStream: ViewInteractionStream): Observable<Boolean> {
        return Observable.never<Boolean>()
            .startWithItem(true)
            .doOnSubscribe { executedRxCount++ }
    }

}

@Suppress("unused")
class MockWrongRxLifecycleListener : LifecycleListener(MockLifecycleSource()) {

    @CreatedToDestroy
    fun executeRx(@Suppress("UNUSED_PARAMETER") wrongParameter: String): Observable<Boolean> {
        return Observable.never<Boolean>()
            .startWithItem(true)
    }

}