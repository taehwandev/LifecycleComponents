package io.androidalatan.lifecycle.handler.sample.bottomsheet

import io.androidalatan.lifecycle.handler.activity.LifecycleBottomSheetDialogFragment
import io.androidalatan.lifecycle.handler.sample.BR
import io.androidalatan.lifecycle.handler.sample.R
import io.androidalatan.view.event.api.ViewAccessor

class SampleBottomSheetDialogFragment : LifecycleBottomSheetDialogFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_sample_bottom_sheet

    override fun viewInit(viewAccessor: ViewAccessor) {
        val adapter = BottomSheetListAdapter()
        SampleBottomSheetViewModel(this, adapter)
        viewAccessor.setVariable(BR.adapter, adapter)
    }
}