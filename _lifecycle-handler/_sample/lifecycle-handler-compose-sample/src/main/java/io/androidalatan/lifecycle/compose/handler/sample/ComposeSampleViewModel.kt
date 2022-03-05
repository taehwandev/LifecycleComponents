package io.androidalatan.lifecycle.compose.handler.sample

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.androidalatan.lifecycle.handler.annotations.async.ResumedToPause
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import io.androidalatan.view.event.api.ViewInteractionStream
import io.androidalatan.view.event.api.toolbar.ToolbarEvent
import io.androidalatan.view.event.api.view.OnSizeChangeEvent
import io.androidalatan.view.event.legacy.flow.asFlow
import io.androidalatan.view.event.legacy.flow.view.onClickAsFlow
import io.androidalatan.view.event.legacy.flow.view.onSizeChangeAsFlow
import io.androidalatan.view.event.legacy.rx.asObservable
import io.androidalatan.view.event.legacy.rx.toolbar.onMenuItemClickAsObservable
import io.androidalatan.view.event.legacy.rx.view.onClickAsObservable
import io.androidalatan.view.event.legacy.rx.view.onSizeChangeAsObservable
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class ComposeSampleViewModel(lifecycleSource: LifecycleSource) : LifecycleListener(lifecycleSource) {

    internal val clickedCount: MutableLiveData<Int> = MutableLiveData(0)

    @ResumedToPause
    fun observeClick(rxViewInteractionStream: ViewInteractionStream): Observable<Long> {
        return rxViewInteractionStream.asObservable()
            .switchMap { view ->
                view.find(R.id.button1)
                    .onClickAsObservable()
                    .doOnNext { clickedCount.value = clickedCount.value!! + 1 }
            }
    }

    @ResumedToPause
    fun observeShareClick(rxViewInteractionStream: ViewInteractionStream): Observable<Int> {
        return rxViewInteractionStream.asObservable()
            .switchMap { view ->
                view.find(R.id.top_app_bar, ToolbarEvent::class.java)
                    .onMenuItemClickAsObservable()
                    .filter { menuId -> menuId == R.id.share }
                    .doOnNext {
                        Log.d(TAG, "observeShareClick: ShareMenu Clicked!!")
                    }
            }
    }

    @ResumedToPause
    fun observeViewSize(rxViewInteractionStream: ViewInteractionStream): Observable<OnSizeChangeEvent.ViewSize> {
        return rxViewInteractionStream.asObservable()
            .switchMap { view ->
                view.find(R.id.button1).onSizeChangeAsObservable()
            }
            .doOnNext {
                Log.i(TAG, "$it")
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @ResumedToPause
    fun flowClick(flowViewInteractionStream: ViewInteractionStream): Flow<Long> =
        flowViewInteractionStream.asFlow()
            .flatMapLatest { view ->
                view.find(R.id.button2)
                    .onClickAsFlow()
            }
            .onEach { clickedCount.value = clickedCount.value!! + 1 }
            .flowOn(Dispatchers.Main)

    @OptIn(ExperimentalCoroutinesApi::class)
    @ResumedToPause
    fun flowViewSize(viewInteractionStream: ViewInteractionStream): Flow<OnSizeChangeEvent.ViewSize> =
        viewInteractionStream.asFlow()
            .flatMapLatest { view ->
                view.find(R.id.button2)
                    .onSizeChangeAsFlow()
            }
            .onEach {
                Log.i(TAG, "$it")
            }

    companion object {
        private const val TAG = "ComposeSampleViewModel"
    }

}