package sparkj.adapter.face;

import android.view.View;

import androidx.annotation.Keep;

import sparkj.adapter.Tag;

/**
 * @author yun.
 * @date 2019/6/2 0002
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
@Keep
public abstract class OnViewBeanClickListener implements View.OnClickListener {

  private static final int SHAKE_TIME = 500;
  private int mShakeTime = SHAKE_TIME;

  public OnViewBeanClickListener(int shakeTime) {
    mShakeTime = shakeTime;
  }

  public OnViewBeanClickListener() {
  }

  @Override
  public void onClick(View v) {
    Object tag = v.getTag(Tag.view_click);
    if (tag != null && (System.currentTimeMillis() - ((Long) tag) < mShakeTime)) {
      return;
    }
    v.setTag(Tag.view_click, System.currentTimeMillis());
    throttleFirstclick(v);
  }

  protected abstract void throttleFirstclick(View v);
}
