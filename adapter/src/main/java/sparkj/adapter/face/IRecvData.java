package sparkj.adapter.face;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import sparkj.adapter.holder.ViewBeanHolder;

/**
 * @another 江祖赟
 * @date 2017/7/5.
 */
@Keep
public interface IRecvData {

  void onViewDetachedFromWindow(@NonNull ViewBeanHolder holder);

  void onViewAttachedToWindow(@NonNull ViewBeanHolder holder);

  void onBindViewHolder(ViewBeanHolder holder, int position, @Nullable List<Object> payloads, @Nullable OnViewClickListener viewClickListener);
}
