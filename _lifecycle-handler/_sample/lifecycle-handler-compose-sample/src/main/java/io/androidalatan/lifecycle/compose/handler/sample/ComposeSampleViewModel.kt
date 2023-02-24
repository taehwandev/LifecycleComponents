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
import io.androidalatan.view.event.legacy.flow.toolbar.onMenuItemClickAsFlow
import io.androidalatan.view.event.legacy.flow.view.onClickAsFlow
import io.androidalatan.view.event.legacy.flow.view.onSizeChangeAsFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class ComposeSampleViewModel(lifecycleSource: LifecycleSource) : LifecycleListener(lifecycleSource) {

    internal val clickedCount: MutableLiveData<Int> = MutableLiveData(0)

    @ResumedToPause
    fun observeClick(rxViewInteractionStream: ViewInteractionStream): Flow<Long> {
        return rxViewInteractionStream.asFlow()
            .flatMapLatest { view ->
                view.find(R.id.button1)
                    .onClickAsFlow()
                    .onEach { clickedCount.value = clickedCount.value!! + 1 }
            }
    }

    @ResumedToPause
    fun observeShareClick(rxViewInteractionStream: ViewInteractionStream): Flow<Int> {
        return rxViewInteractionStream.asFlow()
            .flatMapLatest { view ->
                view.find(R.id.top_app_bar, ToolbarEvent::class.java)
                    .onMenuItemClickAsFlow()
                    .filter { menuId -> menuId == R.id.share }
                    .onEach {
                        Log.d(TAG, "observeShareClick: ShareMenu Clicked!!")
                    }
            }
    }

    @ResumedToPause
    fun observeViewSize(rxViewInteractionStream: ViewInteractionStream): Flow<OnSizeChangeEvent.ViewSize> {
        return rxViewInteractionStream.asFlow()
            .flatMapLatest { view ->
                view.find(R.id.button1).onSizeChangeAsFlow()
            }
            .onEach {
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