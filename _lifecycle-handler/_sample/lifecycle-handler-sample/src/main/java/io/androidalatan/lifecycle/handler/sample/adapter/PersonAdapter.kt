package io.androidalatan.lifecycle.handler.sample.adapter

import androidx.databinding.ViewDataBinding
import io.androidalatan.lifecycle.handler.sample.BR
import io.androidalatan.lifecycle.handler.sample.R
import io.androidalatan.lifecycle.handler.sample.model.Person
import io.androidalatan.rx.recyclerview.adapter.DataBindingViewHolder
import io.androidalatan.rx.recyclerview.adapter.DiffUtilAdapter

class PersonAdapter : DiffUtilAdapter<Person>() {

    override fun getLayoutIdByViewType(viewType: Int): Int {
        return R.layout.row_person
    }

    override fun bindViewModel(viewDataBinding: ViewDataBinding, itemViewType: Int, data: Person) {
        if (itemViewType == 0) {
            super.bindViewModel(viewDataBinding, itemViewType, data)
        } else {
            viewDataBinding.setVariable(BR.person, data)
        }
    }

    override fun newViewHolder(viewDataBinding: ViewDataBinding, viewType: Int): DataBindingViewHolder<Person> {
        return if (viewType == 0) {
            PersonViewHolder(viewDataBinding)
        } else {
            super.newViewHolder(viewDataBinding, viewType)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }
}

class PersonViewHolder(viewDataBinding: ViewDataBinding) : DataBindingViewHolder<Person>(viewDataBinding) {
    override fun setData(data: Person) {
        viewDataBinding.setVariable(BR.person, data.copy(name = "Name : ${data.name}"))
    }
}