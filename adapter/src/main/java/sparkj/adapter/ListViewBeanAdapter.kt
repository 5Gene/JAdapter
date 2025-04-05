package sparkj.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import sparkj.adapter.holder.ViewHolder
import sparkj.adapter.vb.ViewBean

class ListViewBeanAdapter<D: ViewBean>: ListAdapter<D, ViewHolder>(object : DiffUtil.ItemCallback<D>(){
    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
        TODO("Not yet implemented")
    }
}) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}