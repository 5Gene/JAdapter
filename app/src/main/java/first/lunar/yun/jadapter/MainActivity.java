package first.lunar.yun.jadapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sparkj.adapter.JVBrecvAdapter;
import sparkj.adapter.LApp;
import sparkj.adapter.LoadMoreWrapperAdapter;
import sparkj.adapter.face.JOnClickListener;
import sparkj.adapter.face.OnMoreloadListener;
import sparkj.adapter.face.OnViewClickListener;
import sparkj.adapter.holder.JViewHolder;
import sparkj.adapter.vb.JViewBean;
import sparkj.jadapter.R;

public class MainActivity extends AppCompatActivity implements OnViewClickListener<DataTest>, OnMoreloadListener, SwipeRefreshLayout.OnRefreshListener {

    private LoadMoreWrapperAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LApp.setDebug(true);
        List<JViewBean> dataTests = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataTests.add(new DataTest());
        }
//        dataTests.set(3, new MediaVb());

        mRecyclerView = findViewById(R.id.rcv);
        mRefreshLayout = findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LoadMoreWrapperAdapter(new JVBrecvAdapter(dataTests, this), dataTests);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.enAbleLoadMore(true);
        mAdapter.setOnMoreloadListener(this);
    }

    @Override
    public void onItemClicked(View view, DataTest itemData) {
        Toast.makeText(view.getContext(), itemData.text + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onup2LoadingMore() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (new Random().nextBoolean()) {
                    List<DataTest> dataTests = new ArrayList<>();
                    for (int i = 0; i < 20; i++) {
                        dataTests.add(new DataTest());
                    }
                    mAdapter.addMoreList(dataTests);
                } else {
                    if (new Random().nextBoolean()) {
                        mAdapter.loadError();
                    } else {
                        mAdapter.enAbleLoadMore(false, "啦啦啦");
                    }
                }
            }
        }, 1000);
    }

    @Override
    public void retryUp2LoadingMore() {
        onup2LoadingMore();
    }

    @Override
    public void onRefresh() {
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                List<JViewBean> dataTests = new ArrayList<>();
                dataTests.add(new TestVb2());
                for (int i = 0; i < 20; i++) {
                    dataTests.add(new DataTest());
                }

                mAdapter.refreshAllData(dataTests);
                mRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}

class DataTest extends JViewBean {

    String text = "测试:" + String.valueOf(new Random().nextInt());

    @Override
    public int bindLayout() {
        return R.layout.item_test_vb;
    }

    @Override
    public void onBindViewHolder(JViewHolder holder, final int position, @Nullable List<Object> payloads, OnViewClickListener viewClickListener) {
        holder.setText(R.id.tv, position + "    " + text)
                .setOnClickListener(new JOnClickListener() {
                    @Override
                    public void throttleFirstclick(View v) {
                        Toast.makeText(v.getContext(), getPosition() + "", Toast.LENGTH_SHORT).show();
                    }
                }, R.id.iv);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull JViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        System.out.println("onViewDetachedFromWindow - " + getPosition() + " - " + holder);
    }

    @Override
    public void onViewRecycled(@NonNull @NotNull JViewHolder holder) {
        super.onViewRecycled(holder);
        System.out.println("onViewRecycled - " + getPosition() + " - " + holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull @NotNull JViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        System.out.println("onViewAttachedToWindow - " + getPosition() + " - " + holder);
    }
}