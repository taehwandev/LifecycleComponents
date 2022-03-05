package io.androidalatan.lifecycle.handler.sample.bottomsheet

import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

class SampleBottomSheetViewModel(
    lifecycleSource: LifecycleSource,
    private val adapter: BottomSheetListAdapter
) : LifecycleListener(lifecycleSource) {

    @CreatedToDestroy
    fun setAdapterValue(): Single<List<String>> {
        return Observable.just("Share", "Save", "Cancel")
            .toList()
            .compose(adapter.asRxTransformer())
    }
}