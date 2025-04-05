package sparkj.adapter.diff;

import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;
import java.util.List;

import sparkj.adapter.vb.ViewBean;

/**
 * @author yun.
 * @date 2017/9/21
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
@Keep
public class DiffViewBeanCallback<D extends ViewBean> extends DiffUtil.Callback {
    private List<D> oldList = new ArrayList<>();
    private List<D> newList = new ArrayList<>();

    public DiffViewBeanCallback(List<D> oldList, List<D> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).areItemsTheSame(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).areContentsTheSame(newList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getChangePayload(newList.get(newItemPosition));
    }

}
