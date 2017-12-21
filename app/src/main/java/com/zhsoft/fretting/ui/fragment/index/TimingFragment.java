package com.zhsoft.fretting.ui.fragment.index;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.FundResp;
import com.zhsoft.fretting.present.index.TimingPresent;
import com.zhsoft.fretting.ui.adapter.index.TimingRecycleAdapter;
import com.zhsoft.fretting.ui.widget.PopShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xrecyclerview.RecyclerAdapter;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * 作者：sunnyzeng on 2017/12/21 17:49
 * 描述：
 */

public class TimingFragment extends XFragment<TimingPresent> {
    @BindView(R.id.tv_range)
    TextView tvRange;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.content_layout)
    XRecyclerContentLayout contentLayout;
    private int isSelector = 0;
    private TimingRecycleAdapter adapter;
    private List<String> list;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_index_timing;
    }

    @Override
    public TimingPresent newP() {
        return new TimingPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //tab类型 请求接口的时候需要
        String fundTabName = bundle.getString(Constant.FUND_TAB_NAME, "");

        contentLayout.getSwipeRefreshLayout().setColorSchemeResources(
                R.color.color_main,
                cn.droidlover.xrecyclerview.R.color.x_blue,
                cn.droidlover.xrecyclerview.R.color.x_yellow,
                cn.droidlover.xrecyclerview.R.color.x_green
        );

        contentLayout.getRecyclerView().verticalLayoutManager(context);
        contentLayout.getRecyclerView()
                .setAdapter(getAdapter());
        contentLayout.getRecyclerView().horizontalDivider(R.color.color_e7e7e7, R.dimen.dimen_1);  //设置divider

        getP().loadData();
        tvRange.setText(list.get(isSelector));

        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {

                    }

                    @Override
                    public void onLoadMore(int page) {

                    }
                });

        contentLayout.loadingView(View.inflate(getContext(), R.layout.view_loading, null));
        contentLayout.showLoading();
        contentLayout.getRecyclerView().useDefLoadMoreView();

    }

    @Override
    public void initEvents() {
        //涨幅
        tvRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopShow popShow = new PopShow(context, viewLine);
                popShow.showRangeSelector(list, isSelector);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        isSelector = position;
                        tvRange.setText(list.get(position));
                    }
                });
            }
        });
    }

    /**
     * 初始化adapter
     *
     * @return
     */
    public RecyclerAdapter getAdapter() {
        if (adapter == null) {
            adapter = new TimingRecycleAdapter(context);
        }
        return adapter;
    }

    /**
     * 数据展示
     *
     * @param page
     * @param item
     */
    public void showData(int page, List<FundResp> item) {

        list = new ArrayList<>();
        list.add("近一年定投收益率");
        list.add("近两年定投收益率");
        list.add("近三年定投收益率");
        list.add("近五年定投收益率");

        if (item != null && item.size() > 1) {
            if (page > 1) {
                getAdapter().addData(item);
            } else {
                getAdapter().setData(item);
            }
            contentLayout.getRecyclerView().setPage(page, page + 1);
        } else {
            //没有更多数据了
            contentLayout.getRecyclerView().setPage(page, page - 1);
        }
    }


}
