package sparkj.adapter.vb;

import androidx.annotation.Keep;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import sparkj.adapter.face.IRecvDataDiff;
import sparkj.adapter.holder.JViewHolder;

/**
 * @author yun.
 * @date 2019/6/1 0001
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
@Keep
public abstract class JViewBean implements IRecvDataDiff<JViewBean> {

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
    public void onViewDetachedFromWindow(@NonNull JViewHolder holder) {

    }

    @Override
    public void onViewAttachedToWindow(@NonNull JViewHolder holder) {

    }

    public void onViewRecycled(@NonNull JViewHolder holder) {

    }

    @Override
    public boolean areItemsTheSame(@NotNull JViewBean newData) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(@NotNull JViewBean newData) {
        return true;
    }

    @Override
    public @Nullable Object getChangePayload(@NotNull JViewBean newData) {
        return null;
    }
}
