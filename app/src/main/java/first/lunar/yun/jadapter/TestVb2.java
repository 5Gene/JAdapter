package first.lunar.yun.jadapter;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.List;

import sparkj.adapter.face.OnViewBeanClickListener;
import sparkj.adapter.face.OnViewClickListener;
import sparkj.adapter.holder.ViewBeanHolder;
import sparkj.adapter.vb.ViewBean;
import sparkj.jadapter.R;

/**
 * @author yun.
 * @date 2020/8/23 0023
 * @des [一句话描述]
 * @since [https://github.com/mychoices]
 * <p><a href="https://github.com/mychoices">github</a>
 */
public class TestVb2 extends ViewBean {
  private String text = "TestVb2";

  @Override
  public int bindLayout() {
    return R.layout.item_test_vb2;
  }

  @Override
  public void onBindViewHolder(ViewBeanHolder holder, final int position, @Nullable List<Object> payloads, OnViewClickListener viewClickListener) {
    holder.setText(R.id.tv, position + "    " + text)
        .setOnClickListener(new OnViewBeanClickListener() {
          @Override
          public void throttleFirstclick(View v) {
            Toast.makeText(v.getContext(), getPosition() + "", Toast.LENGTH_SHORT).show();
          }
        }, R.id.iv);
  }
}
