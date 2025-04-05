package sparkj.adapter;

import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sparkj.adapter.helper.Damping;


/**
 * @des [recycleview适配器 基类，上拉加载更多,多类型布局,拖拽,滑动删除 支持]
 */
public class LoadMoreWrapperDampAdapter<T> extends LoadMoreWrapperAdapter<T> {

    public boolean mIsNeedDamp = true;

    public LoadMoreWrapperDampAdapter(ViewBeanAdapter innerAdapter) {
        super(innerAdapter);
    }

    public LoadMoreWrapperDampAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> innerAdapter, List data) {
        super(innerAdapter, data);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
        if(mIsNeedDamp) {
            Damping.wrapper(mRecyclerView).configDirection(LinearLayout.VERTICAL);
        }
    }
}
