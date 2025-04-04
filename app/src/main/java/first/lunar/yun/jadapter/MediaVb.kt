package first.lunar.yun.jadapter

import sparkj.adapter.face.OnViewClickListener
import sparkj.adapter.holder.JViewHolder
import sparkj.adapter.vb.JViewBean

class MediaVb: JViewBean() {
    override fun bindLayout(): Int {
        return sparkj.jadapter.R.layout.item_media_vb
    }

    override fun onBindViewHolder(
        holder: JViewHolder?,
        position: Int,
        payloads: List<Any?>?,
        viewClickListener: OnViewClickListener<*>?
    ) {
    }
}