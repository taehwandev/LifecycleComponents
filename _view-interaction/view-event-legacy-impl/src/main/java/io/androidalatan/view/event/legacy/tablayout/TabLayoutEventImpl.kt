package io.androidalatan.view.event.legacy.tablayout

import com.google.android.material.tabs.TabLayout
import io.androidalatan.view.event.api.tablayout.OnTabSelectEvent
import io.androidalatan.view.event.api.tablayout.TabLayoutEvent

class TabLayoutEventImpl(
    tabLayout: TabLayout
) : TabLayoutEvent,
    OnTabSelectEvent by OnTabSelectEventImpl(tabLayout)