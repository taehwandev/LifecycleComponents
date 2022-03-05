package io.androidalatan.view.event.compose.tablayout

import androidx.annotation.IdRes
import io.androidalatan.component.view.compose.api.ComposeViewInteractionObserver
import io.androidalatan.component.view.compose.api.tabs.OnTabSelected
import io.androidalatan.view.event.api.tablayout.OnTabSelectEvent

class OnTabSelectEventImpl(
    @IdRes private val id: Int,
    private val interactionObserver: ComposeViewInteractionObserver
) : OnTabSelectEvent {
    private val callbacks = mutableListOf<OnTabSelectEvent.Callback>()
    private var baseCallback: ComposeViewInteractionObserver.Callback<OnTabSelected>? = null

    override fun registerOnTabSelectCallback(callback: OnTabSelectEvent.Callback) {

        if (callbacks.isEmpty()) {
            val baseCallback = ComposeViewInteractionObserver.Callback<OnTabSelected> { tabSelected ->
                callbacks.forEach {
                    it.onTabSelect(
                        OnTabSelectEvent.SelectedTab(
                            tabSelected.selectedPosition,
                            OnTabSelectEvent.SelectedEventType.SELECTED
                        )
                    )
                }
            }
            this.baseCallback = baseCallback
            interactionObserver.registerCallback(id, OnTabSelected::class.java, true, baseCallback)
        }

        callbacks.add(callback)
    }

    override fun unregisterOnTabSelectCallback(callback: OnTabSelectEvent.Callback) {
        callbacks.remove(callback)
        if (callbacks.isEmpty()) {
            baseCallback = null
            interactionObserver.unregisterCallback(id, OnTabSelected::class.java)
        }
    }
}