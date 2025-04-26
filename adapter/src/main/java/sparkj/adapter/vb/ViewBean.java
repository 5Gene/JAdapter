package sparkj.adapter.vb;

import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import sparkj.adapter.face.IRecvDataDiff;
import sparkj.adapter.holder.ViewBeanHolder;

/**
 * @author yun.
 * @date 2019/6/1 0001
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
@Keep
public abstract class ViewBean implements IRecvDataDiff<ViewBean> {

    private int mPosition;

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    @LayoutRes
    public abstract int bindLayout();

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewBeanHolder holder) {

    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewBeanHolder holder) {

    }

    public void onViewRecycled(@NonNull ViewBeanHolder holder) {

    }

    @Override
    public boolean areItemsTheSame(@NotNull ViewBean newData) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NotNull ViewBean newData) {
        return true;
    }

    @Override
    public @Nullable Object getChangePayload(@NotNull ViewBean newData) {
        return null;
    }
}
