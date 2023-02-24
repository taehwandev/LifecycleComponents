package io.androidalatan.lifecycle.handler.sample.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.androidalatan.lifecycle.handler.sample.BR
import io.androidalatan.lifecycle.handler.sample.R

class BottomSheetListAdapter : RecyclerView.Adapter<TitleViewHolder>() {
    private var list: List<String> = emptyList()

    fun setData(newList: List<String>) {
        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = list.size

            override fun getNewListSize(): Int = newList.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                list[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                list[oldItemPosition] == newList[newItemPosition]
        })

        this.list = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.row_bottom_sheet,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.setData(list[position])
    }
}

class TitleViewHolder(private val viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {

    fun setData(data: String) {
        viewDataBinding.setVariable(BR.title, data)
    }
}