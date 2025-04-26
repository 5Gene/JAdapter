package sparkj.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import sparkj.adapter.holder.ViewBeanHolder
import sparkj.adapter.vb.ViewBean

class ListViewBeanAdapter<D: ViewBean>: ListAdapter<D, ViewBeanHolder>(object : DiffUtil.ItemCallback<D>(){
    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
        TODO("Not yet implemented")
    }

    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
        TODO("Not yet implemented")
    }
}) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBeanHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewBeanHolder, position: Int) {
        TODO("Not yet implemented")
    }
}