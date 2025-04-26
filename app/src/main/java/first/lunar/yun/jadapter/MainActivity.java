package first.lunar.yun.jadapter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sparkj.adapter.LApp;
import sparkj.adapter.LoadMoreWrapperAdapter;
import sparkj.adapter.ViewBeanAdapter;
import sparkj.adapter.custom.MediaSelectViewModel;
import sparkj.adapter.face.OnMoreloadListener;
import sparkj.adapter.face.OnViewBeanClickListener;
import sparkj.adapter.face.OnViewClickListener;
import sparkj.adapter.holder.ViewBeanHolder;
import sparkj.adapter.vb.ViewBean;
import sparkj.jadapter.R;

public class MainActivity extends AppCompatActivity implements OnViewClickListener<DataTest>, OnMoreloadListener, SwipeRefreshLayout.OnRefreshListener {

    private LoadMoreWrapperAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaSelectViewModel mediaSelectViewModel = new ViewModelProvider(this).get(MediaSelectViewModel.class);
//        mediaSelectViewModel.forActivityResult(this::registerForActivityResult);
        mediaSelectViewModel.registerForActivityResult(this::registerForActivityResult);


        LApp.setDebug(true);
        List<ViewBean> dataTests = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataTests.add(new DataTest());
        }
        dataTests.set(3, new MediaVb());

        mRecyclerView = findViewById(R.id.rcv);
        mRefreshLayout = findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new LoadMoreWrapperAdapter(new ViewBeanAdapter(dataTests, this), dataTests);
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
                List<ViewBean> dataTests = new ArrayList<>();
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

class DataTest extends ViewBean {

    String text = "测试:" + String.valueOf(new Random().nextInt());

    @Override
    public int bindLayout() {
        return R.layout.item_test_vb;
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

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewBeanHolder holder) {
        super.onViewDetachedFromWindow(holder);
        System.out.println("onViewDetachedFromWindow - " + getPosition() + " - " + holder);
    }

    @Override
    public void onViewRecycled(@NonNull @NotNull ViewBeanHolder holder) {
        super.onViewRecycled(holder);
        System.out.println("onViewRecycled - " + getPosition() + " - " + holder);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull @NotNull ViewBeanHolder holder) {
        super.onViewAttachedToWindow(holder);
        System.out.println("onViewAttachedToWindow - " + getPosition() + " - " + holder);
    }
}