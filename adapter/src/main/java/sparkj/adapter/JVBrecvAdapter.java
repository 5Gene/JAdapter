package sparkj.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import sparkj.adapter.face.OnViewClickListener;
import sparkj.adapter.helper.CheckHelper;
import sparkj.adapter.helper.LLog;
import sparkj.adapter.holder.JViewHolder;
import sparkj.adapter.vb.JViewBean;

/**
 * @author yun.
 * @date 2019/6/1 0001
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
public class JVBrecvAdapter<D extends JViewBean> extends RecyclerView.Adapter<JViewHolder> implements View.OnClickListener {

    private List<D> mDataList = new ArrayList<>();

    private OnViewClickListener<D> mOnViewClickListener;

    @Keep
    public JVBrecvAdapter(List<D> list) {
        mDataList = list;
    }

    @Keep
    public JVBrecvAdapter(List<D> dataList, OnViewClickListener<D> onViewClickListener) {
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
    public JViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemLayout) {
        return new JViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JViewHolder jViewHolder, int position) {
        this.onBindViewHolder(jViewHolder, position, null);
    }

    @Override
    public void onBindViewHolder(@NonNull JViewHolder holder, int position, @Nullable List<Object> payloads) {
        final D d = mDataList.get(position);
        holder.setHoldVBean(d);
        d.setPosition(position);
        if (mOnViewClickListener != null) {
            JViewHolder.setViewTag(holder.itemView, d);
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
    public void onViewAttachedToWindow(@NonNull JViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        JViewBean holdVBean = holder.getHoldVBean();
        if (holdVBean != null) {
            holdVBean.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull JViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        JViewBean holdVBean = holder.getHoldVBean();
        if (holdVBean != null) {
            holdVBean.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void onViewRecycled(@NonNull JViewHolder holder) {
        super.onViewRecycled(holder);
        JViewBean holdVBean = holder.getHoldVBean();
        if (holdVBean != null) {
            holdVBean.onViewRecycled(holder);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnViewClickListener != null) {
            D d = JViewHolder.getViewTag(v);
            mOnViewClickListener.onItemClicked(v, d);
        }
    }

    @Keep
    public void addMoreList(@NonNull List<D> data) {
        if (CheckHelper.checkLists(data)) {
            int startposition = mDataList.size();
            mDataList.addAll(data);
            notifyItemRangeInserted(startposition, data.size());
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
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mDataList.size();
                }

                @Override
                public int getNewListSize() {
                    return data.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    JViewBean oldItem = mDataList.get(oldItemPosition);
                    JViewBean newItem = data.get(newItemPosition);
                    return oldItem.areItemsTheSame(newItem);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    JViewBean oldItem = mDataList.get(oldItemPosition);
                    JViewBean newItem = data.get(newItemPosition);
                    return oldItem.areContentsTheSame(newItem);
                }
            });
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
