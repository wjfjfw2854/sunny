package cn.wjf.approomorm;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.AbsListView;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cn.wjf.approomorm.base.BaseActivity;
import cn.wjf.approomorm.comm.AutoRefresh;
import cn.wjf.approomorm.databinding.ActivityRankBinding;
import cn.wjf.approomorm.decoration.MyDecoration;
import cn.wjf.approomorm.vm.RankVm;

public class RankActivity extends BaseActivity {

    private ActivityRankBinding db;
    private RankVm vm;
    private AutoRefresh autoRefresh = new AutoRefresh();
    private static int m_nLinesPerPage = 15;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this,R.layout.activity_rank);
//        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
//        ViewModelProvider vp = new ViewModelProvider(this.getViewModelStore(),factory);
//        vm = vp.get(RankVm.class);
        vm = ViewModelProviders.of(this).get(RankVm.class);
        vm.init();
        db.setVm(vm);
        initRv();
        autoRefresh.setOnResfreshListener(new AutoRefresh.OnResfreshListener() {
            @Override
            public void onRefresh() {
                vm.refreshGrid(vm.m_nOffset);
            }
        });
    }

    private void initRv()
    {
        db.rv.addOnScrollListener(listener);
        db.rv.setLayoutManager(new LinearLayoutManager(this));
        db.rv.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST).setDivider(new ColorDrawable(0x363636)));
        db.rv.setAdapter(vm.adapter);
    }

    private RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    int lastVisibleItemPosition;
                    int showCount;
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();//最后1个可见的位置
                        showCount = recyclerView.getChildCount();//当前可见数量
                        int counts = recyclerView.getAdapter().getItemCount();
                        vm.m_nOffset = lastVisibleItemPosition - showCount;
                        if (vm.m_nOffset < 0)
                            vm.m_nOffset = 0;
                        else if (vm.m_nOffset > counts && counts > 0)
                            vm.m_nOffset = counts - m_nLinesPerPage;
                        vm.refreshGrid(vm.m_nOffset);
                    }
                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int visibleItem, int totalItem) {
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        autoRefresh.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoRefresh.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
