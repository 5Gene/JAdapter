package first.lunar.yun.adapter.decoration;

import android.graphics.Rect;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author yun.
 * @date 2017/9/21
 * @des [均分Grid布局 两边对其边框, 中间平分]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class GrideSpreadInsideDecoration extends RecyclerView.ItemDecoration {

    /**
     * first : item left interval
     * sec   : item right interval
     */
    SparseArray<Pair<Integer, Integer>> mCacheIntervalIndex = new SparseArray<>();

    /**
     * 中间边距
     */
    private int mMiddleInterval;
    /**
     * 底部边距
     */
    private int mBottomInterval;
    /**
     * 每个item所在的左下角(左边)x坐标
     */
    private float mItemLeftAbscissa = 0;
    /**
     * 每个item所在的有下角(右边)x坐标
     */
    private float mItemRightAbscissa = 0;

    @Keep
    public GrideSpreadInsideDecoration(int middleInterval, int bottomInterval){
        mMiddleInterval = middleInterval;
        mBottomInterval = bottomInterval;
    }

    @Keep
    public GrideSpreadInsideDecoration(int interval){
        mMiddleInterval = interval;
        mBottomInterval = interval;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, recyclerView, state);

        if (outRect.left + outRect.right > 0) {
            return;
        }

        int position = recyclerView.getChildAdapterPosition(view);
        int totalWidth = recyclerView.getMeasuredWidth() - recyclerView.getPaddingStart() - recyclerView.getPaddingEnd();
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            int spanSize = gridLayoutManager.getSpanSizeLookup().getSpanSize(position);
            if (spanCount != spanSize) {
                throw new RuntimeException("Not yet supported ( SpanSizeLookup )");
            }
            //每个位置所对应的横向第几个位置
            int index = position % spanCount;

            //try to read cache
            Pair<Integer, Integer> intervalOfIndex = mCacheIntervalIndex.get(index);
            if (intervalOfIndex != null ){
                outRect.left = intervalOfIndex.first;
                outRect.right = intervalOfIndex.second;
                outRect.bottom = mBottomInterval;
                return;
            }

            //横向 扣除 中间的间隔之后每个item最多能分配多少空间
            float itemWidthAfterInterval = (totalWidth - mMiddleInterval * (spanCount - 1)) * 1F / spanCount;
            //默认GridLayoutManager为每个item最多能分配多少空间(outRect就是在这个空间里面设置内边距的且设置的最终效果不超过这个空间)
            float itemWidthStanded = totalWidth * 1F / spanCount;

            if (index == 0) {
                //横向第一个item
                mItemLeftAbscissa = 0;
                mItemRightAbscissa = itemWidthAfterInterval;
            }
            float standLeft = itemWidthStanded * index;
            float standRight = itemWidthStanded * (index + 1);
            outRect.left = (int)(mItemLeftAbscissa - standLeft);
            outRect.right = (int)(standRight - mItemRightAbscissa);
            outRect.bottom = mBottomInterval;
            //计算好下一个item左边所在的位置
            mItemLeftAbscissa += itemWidthAfterInterval + mMiddleInterval;
            mItemRightAbscissa += itemWidthAfterInterval + mMiddleInterval;
            //cache
            mCacheIntervalIndex.put(index, Pair.create(outRect.left, outRect.right));
        }

    }
}
