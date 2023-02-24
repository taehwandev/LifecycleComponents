package io.androidalatan.lifecycle.handler.sample.bottomsheet

import io.androidalatan.lifecycle.handler.annotations.async.CreatedToDestroy
import io.androidalatan.lifecycle.handler.api.LifecycleListener
import io.androidalatan.lifecycle.handler.api.LifecycleSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach

class SampleBottomSheetViewModel(
    lifecycleSource: LifecycleSource,
    private val adapter: BottomSheetListAdapter
) : LifecycleListener(lifecycleSource) {

    @CreatedToDestroy
    fun setAdapterValue(): Flow<List<String>> {
        return flowOf("Share", "Save", "Cancel")
            .let { titleFlow ->
                val list = mutableListOf<String>()
                flow<List<String>> {
                    titleFlow.collect { title ->
                        list.add(title)
                    }
                    emit(list)
                }
            }
            .onEach {
                adapter.setData(it)
            }
    }
}