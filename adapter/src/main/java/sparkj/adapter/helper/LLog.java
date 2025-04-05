package sparkj.adapter.helper;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author yun.
 * @date 2019/6/1 0001
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
public class LLog {
  public static void llog(CharSequence... msgs) {
      Log.d("JAdapter", TextUtils.join(",", msgs));
  }
}
