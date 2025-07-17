package sparkj.adapter.decoration

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

//https://blog.csdn.net/carson_ho/article/details/75004649
//https://developer.android.google.cn/develop/ui/views/layout/recyclerview-custom?hl=zh-cn
class GridLayoutDecoration(val verticalOffset: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val index = if (position == -1) {
            //如果是通过notifyRemove删除的item此时被删除的这个会回调且position=-1
            view.getTag(android.R.id.hint).toString().toInt()
        } else {
            view.setTag(android.R.id.hint, position)
            position
        }
        val gridLayoutManager = parent.adapter as GridLayoutManager
        val spanCount = gridLayoutManager.spanCount
        val itemCount = parent.adapter!!.itemCount
        val spanSize = gridLayoutManager.spanSizeLookup.getSpanSize(itemCount)
        val eachWidth = parent.measuredWidth / spanCount
        view.measure(parent.measuredWidth, parent.measuredHeight)
        val rowIndex = index % spanCount
        if (rowIndex == 0) {
            outRect.left = 0
            outRect.right = eachWidth - view.measuredWidth/2
        } else if (rowIndex == spanCount - 1) {
            outRect.right = 0
            outRect.left = eachWidth - view.measuredWidth/2
        } else {
            outRect.right = eachWidth - view.measuredWidth / 2
            outRect.left = eachWidth - view.measuredWidth / 2
        }

        val columnIndex = index / spanCount
        if (columnIndex == 0) {
            outRect.top = verticalOffset
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }
}