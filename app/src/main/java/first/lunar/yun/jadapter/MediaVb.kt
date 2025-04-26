package first.lunar.yun.jadapter

import sparkj.adapter.face.OnViewClickListener
import sparkj.adapter.holder.ViewBeanHolder
import sparkj.adapter.vb.ViewBean

class MediaVb: ViewBean() {
    override fun bindLayout(): Int {
        return sparkj.jadapter.R.layout.item_media_vb
    }

    override fun onBindViewHolder(
        holder: ViewBeanHolder?,
        position: Int,
        payloads: List<Any?>?,
        viewClickListener: OnViewClickListener<*>?
    ) {
    }
}