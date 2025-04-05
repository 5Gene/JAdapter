package sparkj.adapter.face;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import sparkj.adapter.holder.ViewHolder;

/**
 * @another 江祖赟
 * @date 2017/7/5.
 */
@Keep
public interface IRecvData {

  void onViewDetachedFromWindow(@NonNull ViewHolder holder);

  void onViewAttachedToWindow(@NonNull ViewHolder holder);

  void onBindViewHolder(ViewHolder holder, int position, @Nullable List<Object> payloads, @Nullable OnViewClickListener viewClickListener);
}
