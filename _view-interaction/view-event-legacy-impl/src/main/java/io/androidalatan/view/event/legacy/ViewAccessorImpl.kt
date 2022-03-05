package io.androidalatan.view.event.legacy

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.androidalatan.view.event.api.ViewAccessor

class ViewAccessorImpl(private val rootView: View) : ViewAccessor {

    private val viewDataBinding by lazy { DataBindingUtil.bind<ViewDataBinding>(rootView)!! }

    override fun <T> view(id: Int): T {
        return rootView.findViewById(id)
    }

    override fun setVariable(id: Int, variable: Any) {
        viewDataBinding.setVariable(id, variable)
    }
}