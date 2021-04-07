package first.lunar.yun.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import first.lunar.yun.adapter.face.IVBrecvAdapter;
import first.lunar.yun.adapter.face.JOnClickListener;
import first.lunar.yun.adapter.face.OnViewClickListener;
import first.lunar.yun.adapter.helper.LLog;
import first.lunar.yun.adapter.holder.JViewHolder;
import first.lunar.yun.adapter.vb.JViewBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static first.lunar.yun.adapter.helper.CheckHelper.checkLists;

/**
 * @author yun.
 * @date 2019/6/1 0001
 * @des [一句话描述]
 * @since [https://github.com/ZuYun]
 * <p><a href="https://github.com/ZuYun">github</a>
 */
public class JVBrecvAdapter<D extends JViewBean> extends RecyclerView.Adapter<JViewHolder> implements IVBrecvAdapter<D> {

  protected List<D> mDataList = new ArrayList<>();
  private OnViewClickListener<D> mOnViewClickListener;
  JOnClickListener jOnClickListener = new JOnClickListener() {
    @Override
    protected void doClick(View v) {
      if (mOnViewClickListener != null) {
        D d = JViewHolder.getViewTag(v);
        mOnViewClickListener.onItemClicked(v, d);
      }
    }
  };

  @Keep
  public JVBrecvAdapter() {

  }

  @Keep
  public JVBrecvAdapter(OnViewClickListener<D> onViewClickListener) {
    mOnViewClickListener = onViewClickListener;
  }

  @Keep
  @Deprecated
  public JVBrecvAdapter(List<D> list) {
    this(list, null);
  }

  @Keep
  @Deprecated
  public JVBrecvAdapter(List<D> dataList, OnViewClickListener<D> onViewClickListener) {
    this();
    mDataList = dataList;
    mOnViewClickListener = onViewClickListener;
  }

  @Keep
  public List<D> getDataList() {
    return mDataList;
  }

  @Override
  public int getItemViewType(int position) {
    return getDataList().get(position).bindLayout();
  }


  @NonNull
  @Override
  public JViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemLayout) {
    JViewHolder jViewHolder = new JViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false));
    jViewHolder.itemView.setOnClickListener(jOnClickListener);
    return jViewHolder;
  }

  @Override
  public void onBindViewHolder(@NonNull JViewHolder jViewHolder, int position) {
    this.onBindViewHolder(jViewHolder, position, Collections.emptyList());
  }

  @Override
  public void onBindViewHolder(@NonNull JViewHolder holder, int position, @NonNull List<Object> payloads) {
    final D d = getDataList().get(position);
    holder.setHoldVBean(d)
        .setAdatper(this)
        .keepList(getDataList());
    d.setPosition(position);
    if (mOnViewClickListener != null) {
      JViewHolder.setViewTag(holder.itemView, d);
    }
    d.onBindViewHolder(holder, position, payloads, mOnViewClickListener);
  }

  @Keep
  @Override
  public int getItemCount() {
    return getDataList().size();
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

  @Keep
  public void addMoreList(@NonNull List<D> data) {
    if (checkLists(data)) {
      int startposition = mDataList.size();
      mDataList.addAll(data);
      notifyItemRangeInserted(startposition, data.size());
    }
  }

  @Keep
  public void refreshAllData(@NonNull List<D> data) {
    mDataList = data;
    notifyDataSetChanged();
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
