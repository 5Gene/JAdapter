package sparkj.adapter.helper

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sparkj.adapter.ViewBeanAdapter
import sparkj.adapter.face.OnViewClickListener
import sparkj.adapter.holder.ViewBeanHolder
import sparkj.adapter.vb.ViewBean
import kotlin.math.absoluteValue

abstract class ViewDslBean : ViewBean() {

    final override fun bindLayout() = this.hashCode().absoluteValue * -1

    final override fun onBindViewHolder(
        holder: ViewBeanHolder,
        position: Int,
        payloads: List<Any?>?,
        viewClickListener: OnViewClickListener<*>?
    ) {
        (holder.itemView as LinearLayout).onBindViewHolder(
            holder, position, payloads, viewClickListener
        )
    }

    abstract fun LinearLayout.onBindViewHolder(
        holder: ViewBeanHolder,
        position: Int,
        payloads: List<Any?>?,
        viewClickListener: OnViewClickListener<*>?
    )
}

fun ViewGroup.HRecycleView(
    dataList: List<ViewBean>,
    layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(-1, -2),
    onViewClickListener: OnViewClickListener<ViewBean>? = null
) {
    val recyclerView = RecyclerView(context)
    recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
    recyclerView.adapter = ViewBeanAdapter<ViewBean>(dataList, onViewClickListener)
    addView(recyclerView, layoutParams)
}

fun ViewGroup.VRecycleView(
    dataList: List<ViewBean>,
    layoutParams: ViewGroup.LayoutParams = ViewGroup.LayoutParams(-2, -1),
    onViewClickListener: OnViewClickListener<ViewBean>? = null
) {
    val recyclerView = RecyclerView(context)
    recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    recyclerView.adapter = ViewBeanAdapter<ViewBean>(dataList, onViewClickListener)
    addView(recyclerView, layoutParams)
}