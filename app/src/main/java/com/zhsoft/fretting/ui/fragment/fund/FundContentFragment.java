package com.zhsoft.fretting.ui.fragment.fund;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.NewestFundResp;
import com.zhsoft.fretting.present.fund.FundContentPresent;
import com.zhsoft.fretting.ui.adapter.fund.FundContentRecycleAdapter;
import com.zhsoft.fretting.ui.widget.PopShow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XFragment;
import cn.droidlover.xdroidmvp.mvp.XLazyFragment;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xrecyclerview.RecyclerAdapter;
import cn.droidlover.xrecyclerview.XRecyclerContentLayout;
import cn.droidlover.xrecyclerview.XRecyclerView;

/**
 * Created by ${sunny}
 * data: 2017/12/19
 */

public class FundContentFragment extends XFragment<FundContentPresent> {

    @BindView(R.id.tv_range)
    TextView tvRange;
    @BindView(R.id.view_line)
    View viewLine;
    @BindView(R.id.content_layout)
    XRecyclerContentLayout contentLayout;

    private FundContentRecycleAdapter adapter;
    private List<String> list;

    private int isSelector = 0;
    private String fundTabName;
    private int activityName;
    private int pageno = 1;
    private final int pageSize = 10;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fund_content;
    }

    @Override
    public FundContentPresent newP() {
        return new FundContentPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //tab类型 请求接口的时候需要
        fundTabName = bundle.getString(Constant.FUND_TAB_NAME, "");
        //页面（基金页面或者人气产品）
        activityName = bundle.getInt(Constant.ACTIVITY_NAME, 0);

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

        switch (activityName) {
            case Constant.POPULARITY:
                getP().loadPopularityData(pageno, pageSize, fundTabName);
                break;
            case Constant.FUND_INDEX:
                getP().loadFundData(pageno, pageSize, fundTabName, "");
                break;
        }
        list = new ArrayList<>();
        list.add("一周涨幅");
        list.add("近期涨幅");
//        getP().loadData();
        tvRange.setText(list.get(isSelector));

        contentLayout.getRecyclerView()
                .setOnRefreshAndLoadMoreListener(new XRecyclerView.OnRefreshAndLoadMoreListener() {
                    @Override
                    public void onRefresh() {
                        getP().loadFundData(1, 10, fundTabName, "");
                    }

                    @Override
                    public void onLoadMore(int page) {
                        getP().loadFundData(page, pageSize, fundTabName, "");
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
            adapter = new FundContentRecycleAdapter(context,fundTabName);
        }
        return adapter;
    }

    /**
     * 数据展示
     *
     * @param pageno
     * @param item
     */
    public void showData(int pageno, List<NewestFundResp> item) {
//        list = new ArrayList<>();
//        list.add("一周涨幅");
//        list.add("近期涨幅");

        if (item != null && item.size() > 1) {
            if (pageno > 1) {
                getAdapter().addData(item);
            } else {
                getAdapter().setData(item);
            }
            contentLayout.getRecyclerView().setPage(pageno, pageno + 1);
        } else {
            //没有更多数据了
            contentLayout.getRecyclerView().setPage(pageno, pageno - 1);
        }
    }

    public void showError(NetError error) {

    }
}
