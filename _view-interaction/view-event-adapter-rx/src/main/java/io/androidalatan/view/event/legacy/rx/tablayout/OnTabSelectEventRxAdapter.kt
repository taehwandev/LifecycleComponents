package io.androidalatan.view.event.legacy.rx.tablayout

import io.androidalatan.view.event.api.tablayout.OnTabSelectEvent
import io.reactivex.rxjava3.core.Observable

fun OnTabSelectEvent.onTabSelectEventAsObservable(): Observable<OnTabSelectEvent.SelectedTab> {
    return Observable.create { emitter ->
        val callback = OnTabSelectEvent.Callback { selectedTab ->
            emitter.onNext(selectedTab)
        }
        registerOnTabSelectCallback(callback)
        emitter.setCancellable {
            unregisterOnTabSelectCallback(callback)
        }
    }
}

fun OnTabSelectEvent.onIndexTabSelectEventAsObservable(): Observable<Int> {
    return Observable.create { emitter ->
        val callback = OnTabSelectEvent.Callback { selectedTab ->
            if (selectedTab.type == OnTabSelectEvent.SelectedEventType.SELECTED) {
                emitter.onNext(selectedTab.position)
            }
        }
        registerOnTabSelectCallback(callback)
        emitter.setCancellable {
            unregisterOnTabSelectCallback(callback)
        }
    }
}
