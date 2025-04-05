package sparkj.adapter.diff

import androidx.recyclerview.widget.DiffUtil
import sparkj.adapter.vb.ViewBean

class DiffViewBean<D : ViewBean> : DiffUtil.ItemCallback<D>() {
    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}