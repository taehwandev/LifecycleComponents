package io.androidalatan.lifecycle.handler.sample.bottomsheet

import androidx.databinding.ViewDataBinding
import io.androidalatan.lifecycle.handler.sample.BR
import io.androidalatan.lifecycle.handler.sample.R
import io.androidalatan.rx.recyclerview.adapter.DiffUtilAdapter

class BottomSheetListAdapter : DiffUtilAdapter<String>() {
    override fun getLayoutIdByViewType(viewType: Int): Int {
        return R.layout.row_bottom_sheet
    }

    override fun bindViewModel(viewDataBinding: ViewDataBinding, itemViewType: Int, data: String) {
        viewDataBinding.setVariable(BR.title, data)
    }
}