package sparkj.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sparkj.adapter.diff.DiffViewBeanCallback;
import sparkj.adapter.face.OnViewClickListener;
import sparkj.adapter.helper.CheckHelper;
import sparkj.adapter.helper.LLog;
import sparkj.adapter.holder.ViewBeanHolder;
import sparkj.adapter.vb.ViewBean;

/**
 * @author yun.
 * @date 2019/6/1 0001
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
public class ViewBeanAdapter<D extends ViewBean> extends RecyclerView.Adapter<ViewBeanHolder> implements View.OnClickListener {

    private List<D> mDataList = new ArrayList<>();

    private OnViewClickListener<D> mOnViewClickListener;

    @Keep
    public ViewBeanAdapter(List<D> list) {
        mDataList = list;
    }

    @Keep
    public ViewBeanAdapter(List<D> dataList, OnViewClickListener<D> onViewClickListener) {
        mDataList = dataList;
        mOnViewClickListener = onViewClickListener;
    }

    @Keep
    public List<D> getDataList() {
        return mDataList;
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).bindLayout();
    }

    @NonNull
    @Override
    public ViewBeanHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemLayout) {
        //资源ID的结构（32位）是：
        //bits | 意义
        //24-31 | package id（一般是 0x7f）
        //16-23 | type id（资源类型，比如 layout, drawable, string）
        //0-15 | entry id（资源的具体项编号）
//        if (CheckHelper.isLayoutId(itemLayout)) {
        if (itemLayout > 0) {
            return new ViewBeanHolder(LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false));
        } else {
            LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
            linearLayout.setId(View.generateViewId());
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
            return new ViewBeanHolder(linearLayout);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBeanHolder viewBeanHolder, int position) {
        this.onBindViewHolder(viewBeanHolder, position, null);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBeanHolder holder, int position, @Nullable List<Object> payloads) {
        final D d = mDataList.get(position);
        holder.setHoldViewBean(d);
        d.setPosition(position);
        if (mOnViewClickListener != null) {
            ViewBeanHolder.setViewTag(holder.itemView, d);
            holder.itemView.setOnClickListener(this);
        }
        d.onBindViewHolder(holder, position, payloads, mOnViewClickListener);
    }

    @Keep
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewBeanHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewBean holdVBean = holder.getHoldViewBean();
        if (holdVBean != null) {
            holdVBean.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewBeanHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ViewBean holdVBean = holder.getHoldViewBean();
        if (holdVBean != null) {
            holdVBean.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void onViewRecycled(@NonNull ViewBeanHolder holder) {
        super.onViewRecycled(holder);
        ViewBean holdVBean = holder.getHoldViewBean();
        if (holdVBean != null) {
            holdVBean.onViewRecycled(holder);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnViewClickListener != null) {
            D d = ViewBeanHolder.getViewTag(v);
            mOnViewClickListener.onItemClicked(v, d);
        }
    }

    @Keep
    public void addMoreList(@NonNull List<D> data) {
        if (CheckHelper.checkLists(data)) {
            int startPosition = mDataList.size();
            mDataList.addAll(data);
            notifyItemRangeInserted(startPosition, data.size());
        }
    }

    @Keep
    public void refreshAllData(@NonNull List<D> data) {
        changeAllData(data);
    }

    @Keep
    @SuppressLint("NotifyDataSetChanged")
    public void changeAllData(@NonNull List<D> data) {
        if (CheckHelper.checkLists(data)) {
//            new AsyncListDiffer<>(
//                    new AdapterListUpdateCallback(this),
//                    new AsyncDifferConfig.Builder<D>(new DiffViewBean<D>()
//                    ).build()
//            ).submitList(data, () -> {
//                mDataList.clear();
//                mDataList.addAll(data);
//            });
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(
                    new DiffViewBeanCallback<D>(mDataList, data)
            );
            mDataList.clear();
            mDataList.addAll(data);
            diffResult.dispatchUpdatesTo(this);
        }
    }

    @Keep
    public void removeItem(int position) {
        if (position < mDataList.size()) {
            mDataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Keep
    public void removeItem(D item) {
        int index = mDataList.indexOf(item);
        if (index > -1) {
            removeItem(index);
        }
    }

    @Keep
    public void addItem(D data, int position) {
        if (position > mDataList.size()) {
            LLog.llog("JVBrecvAdapter", position + " > mData.size():" + mDataList.size());
            return;
        }
        mDataList.add(position, data);
        notifyItemInserted(position);
    }
}
